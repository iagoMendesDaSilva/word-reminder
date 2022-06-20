package com.iago.reminder.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.helpers.ButtonDefault
import com.iago.reminder.helpers.InputDefault
import com.iago.reminder.navigation.Screens
import com.iago.reminder.ui.theme.White
import com.iago.reminder.utils.GlobalDialogState


@Composable
fun LoginScreen(navController: NavHostController, openGlobalDialog: (dialog: GlobalDialogState) -> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val viewModel = hiltViewModel<LoginViewModel>()

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
            InputDefault(
                value = email,
                label = stringResource(R.string.email),
                icon = Icons.Default.Mail,
                password = false,
                iconDescription = stringResource(R.string.icon_email_desc),
                keyboardType = KeyboardType.Email,
                callback = { email = it }
            )
            Spacer(modifier = Modifier.height(10.dp))
            InputDefault(
                value = password,
                label = stringResource(R.string.Password),
                icon = Icons.Default.Lock,
                password = true,
                iconDescription = stringResource(R.string.icon_password_desc),
                keyboardType = KeyboardType.Password,
                callback = { password = it }
            )
            ForgotPasswordContainer(navController)
        }
        ButtonDefault(stringResource(R.string.button_sign_in), viewModel.loading.value) {
            if (email.isEmpty() || password.isEmpty())
                viewModel.error.value = R.string.error_empty_login
            else
                viewModel.login(email, password) {
                    navController.navigate(Screens.HomeScreen.name)
                }
        }
    }
}

@Composable
fun TitleContainer() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(text = "Let's sign you in.", color = White, style = MaterialTheme.typography.h1)
        Text(
            text = "Welcome back.\nYou've been missed!",
            color = White,
            style = MaterialTheme.typography.h2
        )
    }
}

@Composable
fun ForgotPasswordContainer(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(.85f)
            .padding(top = 5.dp),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = { navController.navigate(Screens.GetStartedScreen.name + "/${false}") }) {
            Text(
                color = White,
                text = "Forgot password?",
                style = MaterialTheme.typography.body1,
            )
        }
    }
}