package com.iago.reminder.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.iago.reminder.database.ReminderDao
import com.iago.reminder.models.Resource
import com.iago.reminder.models.UserModel
import com.iago.reminder.navigation.Screens
import com.iago.reminder.repository.UserRepository
import com.onesignal.OneSignal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val reminderDao: ReminderDao,
    private val repository: UserRepository,
) : ViewModel() {

    fun verifyUser(
        navController: NavHostController,
        callback: () -> Unit,
    ) {
        viewModelScope.launch {
            val user = reminderDao.getUser()
            if (user != null)
                login(user.email, user.password!!, navController, callback)
            else {
                onErrorRefresh(navController, callback)
            }
        }
    }

    fun login(
        email: String,
        password: String,
        navController: NavHostController,
        callback: () -> Unit
    ) {
        viewModelScope.launch {
            when (val response = repository.login(email, password)) {
                is Resource.Success -> {
                    response.data!!.password = password
                    saveOrEditUser(response.data)
                    callback()
                    delay(600)
                    navController.navigate(Screens.HomeScreen.name)
                }
                is Resource.Error -> {
                    onErrorRefresh(navController, callback)
                }
            }
        }
    }

    private fun saveOrEditUser(user: UserModel) = viewModelScope.launch {
        if (reminderDao.existUser())
            reminderDao.editUser(user)
        else
            reminderDao.addUser(user)
    }

    private suspend fun onErrorRefresh(navController: NavHostController, callback: () -> Unit) {
        reminderDao.deleteUser()
        OneSignal.disablePush(true)
        callback()
        delay(600)
        navController.navigate(Screens.LoginScreen.name)
    }
}