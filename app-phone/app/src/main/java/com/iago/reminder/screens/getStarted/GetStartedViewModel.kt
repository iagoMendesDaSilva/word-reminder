package com.iago.reminder.screens.getStarted

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.reminder.R
import com.iago.reminder.database.ReminderDao
import com.iago.reminder.repository.UserRepository
import com.iago.reminder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetStartedViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    var loading = mutableStateOf(false)
    var error = mutableStateOf<Int?>(null)

    fun confirmEmail(email: String, callback: (id: Int) -> Unit) {
        viewModelScope.launch {
            loading.value = true
            error.value = null

            when (val response = repository.confirmEmail(email)) {
                is Resource.Success -> callback(response.data!!.id)
                else -> error.value = response.message
            }

            loading.value = false
        }
    }

    fun verifyEmail(email: String, callback: () -> Unit) {
        viewModelScope.launch {
            loading.value = true
            error.value = null

            when (val response = repository.verifyEmail(email)) {
                is Resource.Success -> error.value = R.string.error_email_registered
                else -> callback()
            }

            loading.value = false
        }
    }

}