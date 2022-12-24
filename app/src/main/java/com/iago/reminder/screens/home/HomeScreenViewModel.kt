package com.iago.reminder.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.reminder.database.ReminderDao
import com.iago.reminder.models.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
            _words.value = reminderDao.getWords()
            loading.value = false
        }
    }

    fun deleteWord(item: Word, cancelAlarm: (word: Word) -> Unit) {
        viewModelScope.launch {
            loadingDelete.value = true
            error.value = null

            reminderDao.deleteWord(item)
            if (item.active)
                cancelAlarm(item)

            val newList = _words.value.toMutableList()
            newList.remove(item)
            _words.value = newList

            loadingDelete.value = false
        }
    }

    fun editActiveWord(
        word: Word,
        createAlarm: (word: Word) -> Unit,
        cancelAlarm: (word: Word) -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                var wordItem =
                    Word(word.id, word.time, word.word, word.word_translate, !word.active)
                reminderDao.editWord(wordItem)

                if (word.active)
                    cancelAlarm(word)
                else
                    createAlarm(word)

                val newList = _words.value.toMutableList()
                newList.find { it.id == word.id }?.active = !word.active
                _words.value = newList
            } catch (e: Exception) {
                onError()
            }
        }
    }
}

