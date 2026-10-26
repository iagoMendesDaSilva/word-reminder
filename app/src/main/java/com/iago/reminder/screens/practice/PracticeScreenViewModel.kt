package com.iago.reminder.screens.practice

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.reminder.R
import com.iago.reminder.ReminderApplication
import com.iago.reminder.models.Word
import com.iago.reminder.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PracticeScreenViewModel(
    private val reminderRepository: ReminderRepository = ReminderApplication.instance.repository
) : ViewModel() {

    var loading = mutableStateOf(true)
    var error = mutableStateOf<Int?>(null)

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words = _words.asStateFlow()

    fun getWords() {
        viewModelScope.launch {
            loading.value = true
            error.value = null
            try {
                _words.value = reminderRepository.getWords()
            } catch (_: Exception) {
                error.value = R.string.error_default
            } finally {
                loading.value = false
            }
        }
    }
}
