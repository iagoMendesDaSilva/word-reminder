package com.iago.reminder.screens.password

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.helpers.ButtonDefault
import com.iago.reminder.helpers.InputDefault
import com.iago.reminder.navigation.Screens
import com.iago.reminder.screens.password.commons.ProgressPassword
import com.iago.reminder.ui.theme.White
import com.iago.reminder.utils.GlobalDialogState

@Composable
fun PasswordScreen(
    navController: NavHostController,
    email: String,
    token: String,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordStrong = remember { mutableStateOf(0f) }

    val context = LocalContext.current
    val viewModel = hiltViewModel<PasswordViewModel>()

    if (viewModel.error.value != null)
        openGlobalDialog(GlobalDialogState(viewModel.error.value!!) {
            viewModel.error.value = null
        })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TitleContainer()
        Column(verticalArrangement = Arrangement.Center) {
            ProgressPassword(passwordStrong.value)
            InputDefault(
                value = password,
                label = stringResource(R.string.password),
                icon = Icons.Default.Lock,
                password = true,
                iconDescription = stringResource(R.string.icon_password_desc),
                keyboardType = KeyboardType.Password,
                callback = {
                    password = it
                    verifyPasswordStrong(password, passwordStrong,context)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            InputDefault(
                value = confirmPassword,
                label = stringResource(R.string.confirm_password),
                icon = Icons.Default.Lock,
                password = true,
                iconDescription = stringResource(R.string.icon_password_desc),
                keyboardType = KeyboardType.Password,
                callback = { confirmPassword = it }
            )
        }
        ButtonDefault(
            if (token.isNullOrEmpty()) stringResource(R.string.button_sing_up)
            else stringResource(R.string.button_reset_password),
            viewModel.loading.value
        ) {
            onClickButton(
                token,
                password,
                viewModel,
                email,
                navController,
                confirmPassword,
                passwordStrong.value,
                openGlobalDialog
            )
        }
    }
}

@Composable
fun TitleContainer() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(R.string.create_password),
            color = White,
            style = MaterialTheme.typography.h1
        )
        Text(
            text = stringResource(R.string.password_desc),
            color = White,
            style = MaterialTheme.typography.body1
        )
    }
}

fun verifyPasswordStrong(password: String, passwordStrong: MutableState<Float>, context:Context) {
    if (!password.isNullOrEmpty()) {
        when {
            context.getString(R.string.regex_strong).toRegex()
                .containsMatchIn(password) -> passwordStrong.value = 1f
            context.getString(R.string.regex_regular).toRegex()
                .containsMatchIn(password) -> passwordStrong.value = .5f
            else -> passwordStrong.value = .25f
        }
    } else passwordStrong.value = 0f
}

fun onClickButton(
    token: String?,
    password: String,
    viewModel: PasswordViewModel,
    email: String,
    navController: NavHostController,
    confirmPassword: String,
    passwordStrong: Float,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {
    if (password.isEmpty() || confirmPassword.isEmpty()) {
        viewModel.error.value = R.string.error_empty_passwords
        return
    }

    if (password != confirmPassword) {
        viewModel.error.value = R.string.error_different_password
        return
    }

    if (passwordStrong < .5f) {
        viewModel.error.value = R.string.error_strong_password
        return
    }

    if (token.isNullOrEmpty())
        viewModel.register(email, password) {
            openGlobalDialog(GlobalDialogState(R.string.success_register, false) {
                navController.navigate(Screens.LoginScreen.name) {
                    popUpTo(0)
                    launchSingleTop = true
                }
            })
        }
    else
        viewModel.recovery(password, token) {
            navController.navigate(Screens.HomeScreen.name)
        }

}