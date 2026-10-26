package com.iago.reminder.screens.home

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iago.reminder.R
import com.iago.reminder.ReminderApplication
import com.iago.reminder.models.Word
import com.iago.reminder.repository.ReminderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeScreenViewModel(
    private val reminderRepository: ReminderRepository = ReminderApplication.instance.repository
) : ViewModel() {

    var loading = mutableStateOf(true)
    var loadingDelete = mutableStateOf(false)
    var error = mutableStateOf<Int?>(null)
    var message = mutableStateOf<Int?>(null)

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words = _words.asStateFlow()

    fun getWords() {
        viewModelScope.launch {
            loading.value = true
            error.value = null
            try {
                val list = reminderRepository.getWords()
                _words.value = list.sortedBy { !it.active }
            } catch (_: Exception) {
                error.value = R.string.error_default
            } finally {
                loading.value = false
            }
        }
    }

    fun importWords(
        context: Context,
        uri: Uri,
        createAlarm: (word: Word, context: Context) -> Unit
    ) {
        viewModelScope.launch {
            loading.value = true
            try {
                val json = withContext(Dispatchers.IO) {
                    context.contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() }
                }
                if (json.isNullOrBlank()) {
                    error.value = R.string.error_default
                    loading.value = false
                    return@launch
                }

                val type = object : TypeToken<List<Word>>() {}.type
                val importedList: List<Word> = Gson().fromJson(json, type)

                withContext(Dispatchers.IO) {
                    for (item in importedList) {
                        val newWord = Word(
                            id = 0,
                            time = item.time,
                            word = item.word,
                            word_translate = item.word_translate,
                            active = item.active
                        )
                        val id = reminderRepository.addWord(newWord)
                        newWord.id = id.toInt()
                        if (newWord.active) {
                            createAlarm(newWord, context)
                        }
                    }
                }
                message.value = R.string.import_success
                getWords()
            } catch (_: Exception) {
                error.value = R.string.error_default
                loading.value = false
            }
        }
    }

    fun exportWords(context: Context, words: List<Word>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                val timestamp = dateFormat.format(Calendar.getInstance().time)
                val fileName = "word_reminder_backup_$timestamp.json"
                val json = Gson().toJson(words)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                        put(MediaStore.MediaColumns.MIME_TYPE, "application/json")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                    }
                    val resolver = context.contentResolver
                    val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                    if (uri != null) {
                        resolver.openOutputStream(uri)?.use { outputStream ->
                            outputStream.write(json.toByteArray())
                        }
                    }
                } else {
                    @Suppress("DEPRECATION")
                    val file = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        fileName
                    )
                    val writer = BufferedWriter(FileWriter(file))
                    writer.write(json)
                    writer.close()
                }

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, context.getString(R.string.export_success), Toast.LENGTH_LONG).show()
                }
            } catch (_: Exception) {
                withContext(Dispatchers.Main) {
                    error.value = R.string.error_default
                }
            }
        }
    }

    fun deleteWord(
        context: Context,
        item: Word,
        cancelAlarm: (word: Word, context: Context) -> Unit
    ) {
        viewModelScope.launch {
            loadingDelete.value = true
            try {
                reminderRepository.deleteWord(item)
                if (item.active) {
                    cancelAlarm(item, context)
                }
                getWords()
            } catch (_: Exception) {
                error.value = R.string.error_default
            } finally {
                loadingDelete.value = false
            }
        }
    }

    fun editActiveWord(
        word: Word,
        context: Context,
        createAlarm: (word: Word, context: Context) -> Unit,
        cancelAlarm: (word: Word, context: Context) -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val updatedWord = word.copy(active = !word.active)
                reminderRepository.editWord(updatedWord)

                if (updatedWord.active) {
                    createAlarm(updatedWord, context)
                } else {
                    cancelAlarm(word, context)
                }

                _words.value = _words.value.map {
                    if (it.id == word.id) updatedWord else it
                }.sortedBy { !it.active }
            } catch (_: Exception) {
                onError()
            }
        }
    }
}
