package com.iago.reminder.screens.wordForm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.reminder.R
import com.iago.reminder.database.ReminderDao
import com.iago.reminder.repository.UserRepository
import com.iago.reminder.repository.WordRepository
import com.iago.reminder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordFormScreenViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val reminderDao: ReminderDao
) : ViewModel() {

    var loading = mutableStateOf(false)
    var error = mutableStateOf<Int?>(null)

    fun saveWord(
        word: String,
        wordTranslate: String,
        time: String,
        active: Boolean,
        callback: () -> Unit
    ) {
        viewModelScope.launch {
            loading.value = true
            error.value = null

            val user = reminderDao.getUser()
            if (user != null)
                when (val response =
                    wordRepository.saveWord(word, wordTranslate, time, active, user.id, user.token!!)) {
                    is Resource.Success -> callback()
                    else -> error.value = response.message
                }
            else
                error.value = R.string.error_default
            loading.value = false
        }
    }

    fun editWord(
        id: Int,
        word: String,
        wordTranslate: String,
        time: String,
        active: Boolean,
        callback: () -> Unit
    ) {
        viewModelScope.launch {
            loading.value = true
            error.value = null

            val user = reminderDao.getUser()
            if (user != null)
                when (val response = wordRepository.editWord(
                    id,
                    word,
                    wordTranslate,
                    time,
                    active,
                    user.id,
                    user.token!!
                )) {
                    is Resource.Success -> callback()
                    else -> error.value = response.message
                }
            else
                error.value = R.string.error_default

            loading.value = false
        }
    }

}