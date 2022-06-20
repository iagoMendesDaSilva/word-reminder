package com.iago.reminder.screens.login.commons

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.navigation.Screens
import com.iago.reminder.ui.theme.White

@Composable
fun ForgetPassword(navController: NavHostController) {
    Text(
        text = stringResource(R.string.LBL_FORGOT_PASSWORD),
        color = White,
        modifier = Modifier.clickable { navController.navigate(Screens.ForgotPasswordScreen.name) }
    )
}