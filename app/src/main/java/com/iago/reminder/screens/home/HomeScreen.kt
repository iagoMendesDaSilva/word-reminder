package com.iago.reminder.screens.home

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.iago.reminder.helpers.Load
import com.iago.reminder.models.Word
import com.iago.reminder.screens.home.commons.Header
import com.iago.reminder.screens.home.commons.ListWords
import com.iago.reminder.screens.home.commons.SearchInput
import com.iago.reminder.ui.theme.Background
import com.iago.reminder.utils.GlobalDialogState

@Composable
fun HomeScreen(
    navController: NavHostController,
    createAlarm: (word: Word, context: android.content.Context) -> Unit,
    cancelAlarm: (word: Word, context: android.content.Context) -> Unit,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit,
    viewModel: HomeScreenViewModel = viewModel()
) {
    BackHandler(enabled = true) {}

    val context = LocalContext.current
    val words by viewModel.words.collectAsState()
    val search = remember { mutableStateOf("") }
    val filter = remember { mutableStateOf<List<Word>>(emptyList()) }

    LaunchedEffect(Unit) {
        viewModel.getWords()
    }

    // Handle error dialog
    if (viewModel.error.value != null) {
        openGlobalDialog(
            GlobalDialogState(
                messageID = viewModel.error.value!!,
                error = true,
                onDismiss = { viewModel.error.value = null }
            )
        )
    }

    // Handle success message dialog
    if (viewModel.message.value != null) {
        openGlobalDialog(
            GlobalDialogState(
                messageID = viewModel.message.value!!,
                error = false,
                onDismiss = { viewModel.message.value = null }
            )
        )
    }

    val importLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            viewModel.importWords(context, uri, createAlarm)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 20.dp)
            .padding(top = 28.dp, bottom = 8.dp)
    ) {
        Header(
            wordCount = words.size,
            activeCount = words.count { it.active },
            import = { importLauncher.launch("application/json") },
            export = { viewModel.exportWords(context, words) },
            openGlobalDialog = openGlobalDialog
        )

        if (words.isNotEmpty()) {
            SearchInput(
                search = search,
                filter = { text ->
                    filter.value = words.filter {
                        it.word.contains(text, ignoreCase = true) ||
                                it.word_translate.contains(text, ignoreCase = true)
                    }
                }
            )
        }

        if (viewModel.loading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Load()
            }
        } else {
            val displayList = if (search.value.isEmpty()) words else filter.value
            ListWords(
                data = displayList,
                navController = navController,
                search = search.value,
                openGlobalDialog = openGlobalDialog,
                context = context,
                viewModel = viewModel,
                createAlarm = createAlarm,
                cancelAlarm = cancelAlarm
            )
        }
    }
}
