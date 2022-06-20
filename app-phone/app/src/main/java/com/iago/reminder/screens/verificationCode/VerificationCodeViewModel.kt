package com.iago.reminder.screens.verificationCode

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.reminder.repository.UserRepository
import com.iago.reminder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationCodeViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    var loading = mutableStateOf(false)
    var error = mutableStateOf<Int?>(null)

    fun confirmCode(id: Int, code: String, callback: (token: String) -> Unit) {
        viewModelScope.launch {
            loading.value = true
            error.value = null

            when (val response = repository.confirmCode(id, code)) {
                is Resource.Success -> callback(response.data!!.token)
                else -> error.value = response.message
            }

            loading.value = false
        }
    }

}