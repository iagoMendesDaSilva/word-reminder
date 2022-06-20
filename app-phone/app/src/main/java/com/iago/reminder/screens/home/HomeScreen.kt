package com.iago.reminder.screens.home

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.screens.home.commons.ButtonAdd
import com.iago.reminder.screens.home.commons.Header
import com.iago.reminder.screens.home.commons.ListWords
import com.iago.reminder.ui.theme.White
import com.iago.reminder.utils.GlobalDialogState
import com.onesignal.OneSignal


@Composable
fun HomeScreen(
    navController: NavHostController,
    connected: Boolean,
    sendToken: (token: String) -> Unit,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit,
) {

    BackHandler(enabled = true) {}

    val context = LocalContext.current
    val viewModel = hiltViewModel<HomeScreenViewModel>()

    val words = viewModel.words.collectAsState().value

    if (viewModel.error.value != null)
        openGlobalDialog(GlobalDialogState(viewModel.error.value!!) {
            viewModel.error.value = null
        })

    LaunchedEffect(key1 = true) {
        viewModel.getWords()
        enableNotification(viewModel)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp, bottom = 10.dp, start = 25.dp, end = 25.dp)
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column {
            Header(connected, viewModel, sendToken, navController, openGlobalDialog)
            if (viewModel.loading.value)
                LoadWords()
            else
                ListWords(words, navController, openGlobalDialog, viewModel)
        }
    }
    ButtonAdd(navController)
}

@Composable
fun LoadWords() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            color = White,
            strokeWidth = 2.dp,
        )
    }
}

fun enableNotification(viewModel: HomeScreenViewModel) {
    OneSignal.disablePush(false)
    val playerID = OneSignal.getDeviceState()?.userId
    if (playerID.isNullOrEmpty())
        viewModel.error.value = R.string.error_notification
    else
        viewModel.saveOneSignalPlayerID(playerID)
}













