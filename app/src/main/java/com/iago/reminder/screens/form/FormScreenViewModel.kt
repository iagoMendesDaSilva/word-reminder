package com.iago.reminder.screens.form

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.reminder.database.ReminderDao
import com.iago.reminder.models.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormScreenViewModel @Inject constructor(
    private val reminderDao: ReminderDao
) : ViewModel() {

    var loading = mutableStateOf(false)
    var error = mutableStateOf<Int?>(null)

    private fun startAction() {
        loading.value = true
        error.value = null
    }

    fun saveWord(
        word: String,
        wordTranslate: String,
        time: String,
        active: Boolean,
        createAlarm: (word: Word) -> Unit,
        callback: () -> Unit
    ) {
        viewModelScope.launch {
            startAction()

            var list = reminderDao.getWords()
            var nextID = if(list.isEmpty()) 1 else list[list.lastIndex].id + 1

            var wordItem = Word(nextID, time, word, wordTranslate, active)
            reminderDao.addWord(wordItem)

            if (active)
                createAlarm(wordItem)

            loading.value = false
            callback()
        }
    }

    fun editWord(
        id: Int,
        word: String,
        wordTranslate: String,
        time: String,
        active: Boolean,
        createAlarm: (word: Word) -> Unit,
        cancelAlarm: (word: Word) -> Unit,
        callback: () -> Unit
    ) {
        viewModelScope.launch {
            startAction()
            var wordItem = Word(id, time, word, wordTranslate, active)
            reminderDao.editWord(wordItem)

            cancelAlarm(wordItem)
            if (active)
                createAlarm(wordItem)

            loading.value = false
            callback()
        }
    }

}