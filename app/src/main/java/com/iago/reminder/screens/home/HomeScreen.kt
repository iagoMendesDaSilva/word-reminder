package com.iago.reminder.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.helpers.Load
import com.iago.reminder.models.Word
import com.iago.reminder.screens.home.commons.Header
import com.iago.reminder.screens.home.commons.ListWords
import com.iago.reminder.utils.GlobalDialogState


@Composable
fun HomeScreen(
    navController: NavHostController,
    createAlarm: (word: Word) -> Unit,
    cancelAlarm: (word: Word) -> Unit,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit,
) {

    BackHandler(enabled = true) {}

    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val words = viewModel.words.collectAsState().value

    if (viewModel.error.value != null)
        openGlobalDialog(GlobalDialogState(viewModel.error.value!!) {
            viewModel.error.value = null
        })

    LaunchedEffect(key1 = true) {
        viewModel.getWords()
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
            Header()
            if (viewModel.loading.value)
                LoadWords()
            else
                ListWords(
                    words,
                    navController,
                    openGlobalDialog,
                    viewModel,
                    createAlarm,
                    cancelAlarm
                )
        }
    }
}

@Composable
fun LoadWords() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Load()
    }
}












