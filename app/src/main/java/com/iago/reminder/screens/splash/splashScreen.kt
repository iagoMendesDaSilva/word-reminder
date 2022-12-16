package com.iago.reminder.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iago.reminder.helpers.Logo
import com.iago.reminder.helpers.MeditationImage
import com.iago.reminder.navigation.Screens
import com.iago.reminder.utils.GlobalDialogState
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {

    LaunchedEffect(key1 = true, block = {
        delay(1500L)
        navController.navigate(Screens.HomeScreen.name)
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MeditationImage()
        Spacer(modifier = Modifier.height(25.dp))
        Logo()
    }
}

