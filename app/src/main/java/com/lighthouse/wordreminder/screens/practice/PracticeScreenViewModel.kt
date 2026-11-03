package com.lighthouse.wordreminder.screens.practice

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lighthouse.wordreminder.R
import com.lighthouse.wordreminder.ReminderApplication
import com.lighthouse.wordreminder.models.Word
import com.lighthouse.wordreminder.repository.ReminderRepository
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
