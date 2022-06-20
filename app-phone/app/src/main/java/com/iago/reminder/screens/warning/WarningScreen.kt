package com.iago.reminder.screens.warning

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iago.reminder.navigation.Screens
import com.iago.reminder.ui.theme.WhiteOpacity

@Composable
fun WarningScreen(navController: NavHostController, warning: String) {

    BackHandler(enabled = true) {
        navController.navigate(Screens.LoginScreen.name) {
            popUpTo(0)
            launchSingleTop = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp), contentAlignment = Alignment.Center
    ) {
        Text(
            text = warning, color = WhiteOpacity,
            textAlign = TextAlign.Center, style = typography.h6
        )
    }
}