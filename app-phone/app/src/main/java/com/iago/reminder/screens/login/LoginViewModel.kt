package com.iago.reminder.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.reminder.database.ReminderDao
import com.iago.reminder.models.UserModel
import com.iago.reminder.repository.UserRepository
import com.iago.reminder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
    private val reminderDao: ReminderDao
) : ViewModel() {

    var loading = mutableStateOf(false)
    var error = mutableStateOf<Int?>(null)

    fun login(email: String, password: String, callback: () -> Unit) {
        viewModelScope.launch {
            loading.value = true
            error.value = null

            when (val response = repository.login(email, password)) {
                is Resource.Success -> {
                    response.data!!.password = password
                    saveOrEditUser(response.data)
                    callback()
                }
                else -> error.value = response.message
            }

            loading.value = false
        }
    }

    private suspend fun saveOrEditUser(user: UserModel) {
        if (reminderDao.existUser())
            reminderDao.editUser(user)
        else
            reminderDao.addUser(user)
    }

}