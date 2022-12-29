package com.iago.reminder.screens.home

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.helpers.Load
import com.iago.reminder.models.Word
import com.iago.reminder.screens.home.commons.Header
import com.iago.reminder.screens.home.commons.ListWords
import com.iago.reminder.screens.home.commons.SearchInput
import com.iago.reminder.utils.GlobalDialogState


@Composable
fun HomeScreen(
    navController: NavHostController,
    createAlarm: (word: Word) -> Unit,
    cancelAlarm: (word: Word) -> Unit,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit,
) {

    BackHandler(enabled = true) {}

    val context = LocalContext.current
    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val words = viewModel.words.collectAsState().value
    val file = remember { mutableStateOf<Uri?>(null) }
    val search = remember { mutableStateOf("") }
    val filter = remember { mutableStateOf<List<Word>>(emptyList()) }

    if (viewModel.error.value != null)
        openGlobalDialog(GlobalDialogState(viewModel.error.value!!) {
            viewModel.error.value = null
        })

    LaunchedEffect(key1 = true) {
        viewModel.getWords()
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        file.value = it
    }

    if (file.value != null) {
        viewModel.importWords(LocalContext.current, file.value!!, cancelAlarm)
        file.value = null
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
            Header(
                import = { launcher.launch("*/*") },
                export = { viewModel.exportWords(context, words) },
                openGlobalDialog,
            )
            if (words.isNotEmpty())
                SearchInput(search) { text ->
                    filter.value = words.filter { it.word.lowercase().contains(text.lowercase()) }
                }
            if (viewModel.loading.value)
                LoadWords()
            else
                ListWords(
                    if (search.value.isEmpty()) words else filter.value,
                    navController,
                    search.value,
                    openGlobalDialog,
                    viewModel,
                    createAlarm,
                    cancelAlarm,
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














