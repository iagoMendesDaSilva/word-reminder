package com.iago.reminder.screens.vocabulary

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.helpers.Load
import com.iago.reminder.models.Dictionary
import com.iago.reminder.screens.vocabulary.commons.Definition
import com.iago.reminder.screens.vocabulary.commons.Header
import com.iago.reminder.screens.vocabulary.commons.SubTitle
import com.iago.reminder.utils.GlobalDialogState
import com.iago.reminder.utils.errorWord

@Composable
fun VocabularyScreen(
    navController: NavHostController,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {

    BackHandler(enabled = true) {}

    val context = LocalContext.current
    val viewModel = hiltViewModel<VocabularyScreenViewModel>()

    val word = viewModel.word.collectAsState().value
//    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.loading.value)

    if (viewModel.error.value != null)
        openGlobalDialog(GlobalDialogState(viewModel.error.value!!) {
            viewModel.error.value = null
        })

    LaunchedEffect(key1 = true) {
        if (word == null)
            viewModel.getWord()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        if (viewModel.loading.value)
            Load(3.dp)
        else
            WordDict(word ?: errorWord, context, viewModel::getWord)
    }
}

@Composable
fun WordDict(
    word: Dictionary,
    context: Context,
    onRefresh: () -> Unit
) {
//    SwipeRefresh(state = swipeRefreshState, onRefresh = onRefresh) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
        ) {
            item {
                Header(word = word.word, context = context)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp)
                ) {
                    word.meanings.forEach { meaning ->
                        SubTitle(meaning.partOfSpeech)
                        meaning.definitions.forEach { definition ->
                            Definition(definition = definition.definition)
                        }
                    }
                }
            }
        }
    }
//}