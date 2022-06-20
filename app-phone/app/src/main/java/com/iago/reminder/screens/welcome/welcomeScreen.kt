package com.iago.reminder.screens.welcome

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.helpers.ButtonDefault
import com.iago.reminder.helpers.Logo
import com.iago.reminder.helpers.MeditationImage
import com.iago.reminder.helpers.OutlinedButtonDefault
import com.iago.reminder.navigation.Screens
import com.iago.reminder.utils.GlobalDialogState

@Composable
fun WelcomeScreen(navController: NavHostController, openGlobalDialog: (dialog: GlobalDialogState) -> Unit) {

    BackHandler(enabled = true) {}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Logo()
        MeditationImageBox()
        Column() {
            ButtonDefault(stringResource(R.string.button_get_started)) {
                navController.navigate(Screens.GetStartedScreen.name + "/${true}")
            }
            OutlinedButtonDefault(stringResource(R.string.button_have_account)) {
                navController.navigate(Screens.LoginScreen.name)
            }
        }

    }
}

@Composable
fun MeditationImageBox() {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        MeditationImage()
    }
}