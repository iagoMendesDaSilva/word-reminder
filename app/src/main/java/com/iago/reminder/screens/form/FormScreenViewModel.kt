package com.iago.reminder.screens.form

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.reminder.R
import com.iago.reminder.ReminderApplication
import com.iago.reminder.models.Word
import com.iago.reminder.repository.ReminderRepository
import kotlinx.coroutines.launch

class FormScreenViewModel(
    private val reminderRepository: ReminderRepository = ReminderApplication.instance.repository
) : ViewModel() {

    var loading = mutableStateOf(false)
    var error = mutableStateOf<Int?>(null)
    var wordLoaded = mutableStateOf<Word?>(null)

    fun loadWord(id: Int) {
        viewModelScope.launch {
            try {
                wordLoaded.value = reminderRepository.getWord(id)
            } catch (_: Exception) {
                error.value = R.string.error_default
            }
        }
    }

    fun saveWord(
        context: Context,
        word: String,
        wordTranslate: String,
        time: String,
        active: Boolean,
        createAlarm: (word: Word, context: Context) -> Unit,
        callback: () -> Unit
    ) {
        viewModelScope.launch {
            loading.value = true
            error.value = null
            try {
                val wordItem = Word(
                    id = 0,
                    time = time,
                    word = word.trim(),
                    word_translate = wordTranslate.trim(),
                    active = active
                )
                val newId = reminderRepository.addWord(wordItem)
                wordItem.id = newId.toInt()

                if (active) {
                    createAlarm(wordItem, context)
                }
                callback()
            } catch (_: Exception) {
                error.value = R.string.error_default
            } finally {
                loading.value = false
            }
        }
    }

    fun editWord(
        context: Context,
        id: Int,
        word: String,
        wordTranslate: String,
        time: String,
        active: Boolean,
        createAlarm: (word: Word, context: Context) -> Unit,
        cancelAlarm: (word: Word, context: Context) -> Unit,
        callback: () -> Unit
    ) {
        viewModelScope.launch {
            loading.value = true
            error.value = null
            try {
                val wordItem = Word(
                    id = id,
                    time = time,
                    word = word.trim(),
                    word_translate = wordTranslate.trim(),
                    active = active
                )
                reminderRepository.editWord(wordItem)

                cancelAlarm(wordItem, context)
                if (active) {
                    createAlarm(wordItem, context)
                }
                callback()
            } catch (_: Exception) {
                error.value = R.string.error_default
            } finally {
                loading.value = false
            }
        }
    }
}
