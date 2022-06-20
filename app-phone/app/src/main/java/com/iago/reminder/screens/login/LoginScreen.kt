package com.iago.reminder.screens.login

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.helpers.Logo
import com.iago.reminder.screens.login.commons.ButtonDefault
import com.iago.reminder.screens.login.commons.ForgetPassword
import com.iago.reminder.screens.login.commons.Input
import com.iago.reminder.screens.login.commons.SignUp

@Composable
fun LoginScreen(navController: NavHostController) {

    val context = LocalContext.current
    val loginViewModel = hiltViewModel<LoginViewModel>()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    BackHandler(enabled = true) {}

    LaunchedEffect(key1 = loginViewModel.error.value) {
        if (!loginViewModel.error.value.isNullOrEmpty())
            Toast.makeText(context, loginViewModel.error.value, Toast.LENGTH_SHORT).show()
    }



    LazyColumn(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Logo()
            Input(
                email,
                "Email",
                Icons.Default.Email,
                false,
                stringResource(R.string.DESCRIPTION_ICON_USER)
            ) { email = it }
            Input(
                password,
                "Password",
                Icons.Default.Lock,
                true,
               stringResource(R.string.DESCRIPTION_ICON_PASS)
            ) { password = it }
            Spacer(modifier = Modifier.height(10.dp))
            ButtonDefault(stringResource(R.string.BTN_LOGIN), loginViewModel.loading.value, navController) {
                loginViewModel.login(email, password, navController)
            }
            ForgetPassword(navController)
            SignUp(navController)
        }
    }
}





