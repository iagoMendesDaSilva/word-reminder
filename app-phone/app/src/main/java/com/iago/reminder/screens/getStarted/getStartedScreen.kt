package com.iago.reminder.screens.getStarted

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
fun GetStartedScreen(
    navController: NavHostController,
    register: Boolean,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {

    var email by remember { mutableStateOf("") }
    val viewModel = hiltViewModel<GetStartedViewModel>()

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
        TitleContainer(register)
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
        }
        ButtonDefault(stringResource(R.string.button_next), viewModel.loading.value) {
            onClickButton(email, register, navController, viewModel)
        }
    }
}


@Composable
fun TitleContainer(register: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            color = White,
            style = MaterialTheme.typography.h1,
            text = if (register) stringResource(R.string.get_started)
            else stringResource(R.string.forgot_password),
        )
        Text(
            text = stringResource(
                R.string.get_started_desc,
                if (register) stringResource(R.string.register_desc)
                else stringResource(R.string.forgot_password_desc)
            ),
            color = White,
            style = MaterialTheme.typography.body1
        )
    }
}


fun onClickButton(
    email: String,
    register: Boolean,
    navController: NavHostController,
    viewModel: GetStartedViewModel
) {
    if (email.isEmpty())
        viewModel.error.value = R.string.error_empty_email
    else {
        if (register)
            viewModel.verifyEmail(email) {
                navController.navigate(Screens.PasswordScreen.name + "/${email}")
            }
        else
            viewModel.confirmEmail(email) { id ->
                navController.navigate(Screens.VerificationCodeScreen.name + "/${email}" + "/${id}")
            }
    }
}