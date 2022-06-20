package com.iago.reminder.screens.login.commons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.navigation.Screens
import com.iago.reminder.ui.theme.Primary

@Composable
fun SignUp(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 5.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(text = stringResource(R.string.LBL_SIGN_UP), color = Primary, modifier = Modifier.clickable {
            navController.navigate(Screens.RegisterScreen.name)
        })
    }
}