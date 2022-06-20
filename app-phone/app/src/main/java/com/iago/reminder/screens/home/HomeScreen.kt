package com.iago.reminder.screens.home

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.ContextProvider
import com.iago.reminder.R
import com.iago.reminder.models.WordModel
import com.iago.reminder.screens.home.commons.AddWord
import com.iago.reminder.screens.home.commons.ListWords
import com.iago.reminder.screens.home.commons.ModalWord
import com.iago.reminder.screens.home.commons.Title
import com.onesignal.OneSignal

@Composable
fun HomeScreen(
    navController: NavHostController,
    connected: Boolean,
    sendToken: (token: String) -> Unit
) {

    val context = LocalContext.current
    val homeViewModel = hiltViewModel<HomeViewModel>()

    BackHandler(enabled = true) {}

    val words = homeViewModel.words.collectAsState().value
    val modalVisible = remember { mutableStateOf(false) }
    val itemModal = remember { mutableStateOf<WordModel?>(null) }

    LaunchedEffect(key1 = true, block = {
        homeViewModel.getAllWords()
        enableNotification(context, homeViewModel)
    })

    LaunchedEffect(key1 = homeViewModel.error.value, block = {
        if (!homeViewModel.error.value.isNullOrEmpty())
            Toast.makeText(context, homeViewModel.error.value, Toast.LENGTH_SHORT)
                .show()
    })


    Column() {
        Title(navController, connected, sendToken)
        ListWords(words, modalVisible, itemModal)
        ModalWord(modalVisible, itemModal)
    }
    AddWord(modalVisible)
}

fun enableNotification(context: Context, homeViewModel: HomeViewModel) {
    OneSignal.disablePush(false)
    val playerID = OneSignal.getDeviceState()?.userId
    if (playerID.isNullOrEmpty())
        Toast.makeText(
            context, ContextProvider.getContext().getString(
                R.string.ERROR_ONESIGNAL
            ), Toast.LENGTH_SHORT
        )
            .show()
    else
        homeViewModel.saveOneSignalPlayerID(playerID)
}