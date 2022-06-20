package com.iago.reminder.screens.password

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.ContextProvider
import com.iago.reminder.R
import com.iago.reminder.screens.login.commons.ButtonDefault
import com.iago.reminder.screens.login.commons.Input
import com.iago.reminder.screens.password.commons.ProgressPassword

@Composable
fun PasswordScreen(navController: NavHostController, email: String, token: String) {

    val context = LocalContext.current
    val passwordViewModel = hiltViewModel<PasswordViewModel>()

    var password by remember { mutableStateOf("") }
    var passwordStrong = remember { mutableStateOf(0f) }
    var confirmPassword by remember { mutableStateOf("") }

    LaunchedEffect(key1 = passwordViewModel.error.value) {
        if (!passwordViewModel.error.value.isNullOrEmpty())
            Toast.makeText(context, passwordViewModel.error.value, Toast.LENGTH_SHORT).show()
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProgressPassword(passwordStrong.value)
        Input(
            password,
            "Password",
            Icons.Default.Lock,
            true,
            stringResource(R.string.DESCRIPTION_ICON_PASS)
        ) {
            password = it
            verifyPasswordStrong(password, passwordStrong)
        }
        Input(
            confirmPassword,
            "Confirm Password",
            Icons.Default.Lock,
            true,
            stringResource(R.string.DESCRIPTION_ICON_PASS)
        ) { confirmPassword = it }
        ButtonDefault(
            stringResource(R.string.BTN_NEXT),
            passwordViewModel.loading.value,
            navController
        ) {
            if (token ==  ContextProvider.getContext().getString(R.string.EMPTY_TOKEN)) {
                register(
                    passwordStrong.value,
                    password,
                    confirmPassword,
                    context,
                    passwordViewModel,
                    email,
                    navController
                )
            } else {
                passwordViewModel.recovery(password, token, navController)
            }
        }
    }
}

fun register(
    passwordStrong: Float,
    password: String,
    confirmPassword: String,
    context: Context,
    passwordViewModel: PasswordViewModel,
    email: String,
    navController: NavHostController
) {
    if (!password.isNullOrEmpty())
        if (password == confirmPassword)
            if (passwordStrong >= .5f)
                passwordViewModel.register(email, password, navController)
            else
                Toast.makeText(
                    context,
                     ContextProvider.getContext().getString(R.string.ERROR_PASSWORD_WEAK),
                    Toast.LENGTH_SHORT
                ).show()
        else
            Toast.makeText(
                context,
                 ContextProvider.getContext().getString(R.string.ERROR_PASSWORD_DIFFERENT),
                Toast.LENGTH_SHORT
            )
                .show()
    else
        Toast.makeText(
            context,
             ContextProvider.getContext().getString(R.string.ERROR_EMPTY),
            Toast.LENGTH_SHORT
        )
            .show()

}

fun verifyPasswordStrong(password: String, passwordStrong: MutableState<Float>) {
    if (!password.isNullOrEmpty()) {
        when {
             ContextProvider.getContext().getString(R.string.REGEX_STRONG_PASSWORD).toRegex()
                .containsMatchIn(password) -> passwordStrong.value = 1f
             ContextProvider.getContext().getString(R.string.REGEX_MEDIUM_PASSWORD).toRegex()
                .containsMatchIn(password) -> passwordStrong.value = .5f
            else -> passwordStrong.value = .25f
        }
    } else passwordStrong.value = 0f
}
