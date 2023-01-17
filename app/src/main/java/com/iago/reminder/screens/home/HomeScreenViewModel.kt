package com.iago.reminder.screens.home

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iago.reminder.R
import com.iago.reminder.database.ReminderDao
import com.iago.reminder.models.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.*
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val reminderDao: ReminderDao
) : ViewModel() {

    var loading = mutableStateOf(true)
    var loadingDelete = mutableStateOf(false)
    var error = mutableStateOf<Int?>(null)

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words = _words.asStateFlow()

    fun getWords() {
        viewModelScope.launch {
            loading.value = true
            error.value = null
            _words.value = reminderDao.getWords().reversed().sortedBy { !it.active }
            loading.value = false
        }
    }

    fun importWords(context: Context, uri: Uri, createAlarm: (word: Word, context:Context) -> Unit) {
        try {
            val file = context.contentResolver.openFileDescriptor(uri, "r")
            val fileDescriptor: FileDescriptor = file!!.fileDescriptor

            val myType = object : TypeToken<List<Word>>() {}.type
            val json = FileInputStream(fileDescriptor).bufferedReader().use { it.readText() }
            val listWord = Gson().fromJson<List<Word>>(json, myType)
            saveWord(listWord, createAlarm, context)
        } catch (e: Exception) {
            error.value = R.string.error_default
        }
    }

    fun exportWords(context: Context, words: List<Word>) {
        try {
            val json = Gson().toJson(words).toString()
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                "reminder_my_words.json"
            )
            val fileWriter = FileWriter(file)
            val bufferedWriter = BufferedWriter(fileWriter)
            bufferedWriter.write(json)
            bufferedWriter.close()
            Toast.makeText(context, "File exported to Documents", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            error.value = R.string.error_default
        }

    }

    private fun saveWord(
        words: List<Word>,
        createAlarm: (word: Word, context: Context) -> Unit,
        context: Context
    ) {
        viewModelScope.launch {
            var list = reminderDao.getWords()
            var nextID = if (list.isEmpty()) 1 else list[list.lastIndex].id + 1

            words.forEach { word ->
                var wordItem = Word(nextID, word.time, word.word, word.word_translate, word.active)
                reminderDao.addWord(wordItem)

                if (word.active)
                    createAlarm(wordItem, context)

                nextID++
            }
            getWords()
        }
    }

    fun deleteWord(context: Context,item: Word, cancelAlarm: (word: Word, context: Context) -> Unit) {
        viewModelScope.launch {
            loadingDelete.value = true
            error.value = null

            reminderDao.deleteWord(item)
            if (item.active)
                cancelAlarm(item, context)

            getWords()

            loadingDelete.value = false
        }
    }

    fun editActiveWord(
        word: Word,
        context: Context,
        createAlarm: (word: Word,context:Context) -> Unit,
        cancelAlarm:  (word: Word,context:Context) -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                var wordItem =
                    Word(word.id, word.time, word.word, word.word_translate, !word.active)
                reminderDao.editWord(wordItem)

                if (word.active)
                    cancelAlarm(word,context)
                else
                    createAlarm(word,context)

                val newList = _words.value.toMutableList()
                newList.find { it.id == word.id }?.active = !word.active
                _words.value = newList
            } catch (e: Exception) {
                onError()
            }
        }
    }
}

