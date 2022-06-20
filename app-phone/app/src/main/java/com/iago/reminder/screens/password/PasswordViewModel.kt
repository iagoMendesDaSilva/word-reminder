package com.iago.reminder.screens.password

import android.content.res.Resources
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.iago.reminder.ContextProvider
import com.iago.reminder.R
import com.iago.reminder.database.ReminderDao
import com.iago.reminder.models.Resource
import com.iago.reminder.models.UserModel
import com.iago.reminder.navigation.Screens
import com.iago.reminder.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val repository: UserRepository,
    private val reminderDao: ReminderDao
) : ViewModel() {

    var loading = mutableStateOf(false)
    var error = mutableStateOf<String?>("")

    private fun startValuesToRequest() {
        loading.value = true
        error.value = ""
    }

    fun register(email: String, password: String, navController: NavHostController) {
        viewModelScope.launch {
            startValuesToRequest()
            when (val response = repository.register(email, password)) {
                is Resource.Success -> {
                    navController.navigate(Screens.WarningScreen.name + "/${
                         ContextProvider.getContext().getString(
                        R.string.WARNING_EMAIL_SENT)}")
                }
                is Resource.Error -> error.value = response.message
            }
            loading.value = false
        }
    }

    fun recovery(password: String, token: String, navController: NavHostController) {
        viewModelScope.launch {
            startValuesToRequest()
            when (val response = repository.recovery(password, token)) {
                is Resource.Success -> {
                    response.data!!.password = password
                    saveOrEditUser(response.data!!)
                    navController.navigate(Screens.HomeScreen.name)
                }
                is Resource.Error -> error.value = response.message
            }
            loading.value = false
        }
    }


    private fun saveOrEditUser(user: UserModel) = viewModelScope.launch {
        if (reminderDao.existUser())
            reminderDao.editUser(user)
        else
            reminderDao.addUser(user)
    }

}