package com.iago.reminder.screens.forgotPassword

import android.content.res.Resources
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.ContextProvider
import com.iago.reminder.R
import com.iago.reminder.helpers.Logo
import com.iago.reminder.screens.login.commons.ButtonDefault
import com.iago.reminder.screens.login.commons.Input

@Composable
fun ForgotPasswordScreen(navController: NavHostController) {

    val context = LocalContext.current
    val forgotPasswordViewModel = hiltViewModel<ForgotPasswordViewModel>()

    var email by remember { mutableStateOf("") }

    LaunchedEffect(key1 = forgotPasswordViewModel.error.value) {
        if (!forgotPasswordViewModel.error.value.isNullOrEmpty())
            Toast.makeText(context, forgotPasswordViewModel.error.value, Toast.LENGTH_SHORT).show()
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Logo()
        Input(
            email,
            "Email",
            Icons.Default.Email,
            false,
            stringResource(R.string.DESCRIPTION_ICON_USER)
        ) { email = it }
        ButtonDefault(stringResource(R.string.BTN_NEXT), forgotPasswordViewModel.loading.value, navController) {
            if (email.isNullOrEmpty())
                Toast.makeText(context,  ContextProvider.getContext().getString(R.string.ERROR_EMPTY), Toast.LENGTH_SHORT).show()
            else
                forgotPasswordViewModel.confirmEmail(email, navController)
        }
    }
}