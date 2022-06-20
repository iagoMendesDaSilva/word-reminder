package com.iago.reminder.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.iago.reminder.screens.home.commons.ItemExpanded
import com.iago.reminder.screens.home.commons.ListWords
import com.iago.reminder.screens.home.commons.Load
import com.iago.reminder.screens.home.commons.Refresh
import com.iago.reminder.theme.BackGround

@Composable
fun HomeScreen(token: String) {

    val context = LocalContext.current
    val listState = rememberScalingLazyListState()
    val homeViewModel = hiltViewModel<HomeViewModel>()

    val words = homeViewModel.words.collectAsState().value
    val indexItem = remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(key1 = true, block = {
        homeViewModel.getAllWords(token)
    })

    LaunchedEffect(key1 = homeViewModel.error.value, block = {
        if (!homeViewModel.error.value.isNullOrEmpty())
            Toast.makeText(context, homeViewModel.error.value, Toast.LENGTH_SHORT)
                .show()
    })


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackGround),
        timeText = {
            if (!listState.isScrollInProgress)
                TimeText()
        },
        vignette = { VignettePosition.TopAndBottom },
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
    ) {
        if (homeViewModel.error.value.isNullOrEmpty())
            ListWords(listState, words) { indexItem.value = it }
        else
            Refresh() { homeViewModel.getAllWords(token) }

        if(homeViewModel.loading.value)
            Load()

        if (indexItem.value != null)
            ItemExpanded(words, indexItem)
    }
}


