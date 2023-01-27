package com.iago.reminder.screens.practice

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
class PracticeScreenViewModel @Inject constructor(
    private val reminderDao: ReminderDao
) : ViewModel() {

    var loading = mutableStateOf(true)
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

}
