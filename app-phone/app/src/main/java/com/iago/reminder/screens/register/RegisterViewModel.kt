package com.iago.reminder.screens.register

import android.content.res.Resources
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.iago.reminder.ContextProvider
import com.iago.reminder.R
import com.iago.reminder.models.Resource
import com.iago.reminder.navigation.Screens
import com.iago.reminder.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: UserRepository) : ViewModel(){

    var loading = mutableStateOf(false)
    var error = mutableStateOf<String?>("")

    private fun startValuesToRequest() {
        loading.value = true
        error.value = ""
    }

    fun verifyEmail(email: String, navController: NavHostController) {
        viewModelScope.launch {
            startValuesToRequest()
            when (val response = repository.verifyEmail(email)) {
                is Resource.Success -> {
                    navController.navigate(Screens.PasswordScreen.name+"/${email}/${
                         ContextProvider.getContext().getString(
                            R.string.EMPTY_TOKEN)}")
                }
                is Resource.Error -> error.value = response.message
            }
            loading.value = false
        }
    }

}

