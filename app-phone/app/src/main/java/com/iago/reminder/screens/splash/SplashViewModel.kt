package com.iago.reminder.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.reminder.database.ReminderDao
import com.iago.reminder.models.UserModel
import com.iago.reminder.repository.UserRepository
import com.iago.reminder.utils.Resource
import com.onesignal.OneSignal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: UserRepository,
    private val reminderDao: ReminderDao
) : ViewModel() {


    suspend fun verifyUser(): UserModel? = reminderDao.getUser()

    fun login(email: String, password: String, callback: (success: Boolean) -> Unit) {
        viewModelScope.launch {
            when (val response = repository.login(email, password)) {
                is Resource.Success -> {
                    response.data!!.password=password
                    saveOrEditUser(response.data)
                    callback(true)
                }
                else -> callback(false)
            }

        }
    }

    private suspend fun saveOrEditUser(user: UserModel) {
        if (reminderDao.existUser())
            reminderDao.editUser(user)
        else
            reminderDao.addUser(user)
    }

    suspend fun onErrorRefresh() {
        reminderDao.deleteUser()
        OneSignal.disablePush(true)
    }

}