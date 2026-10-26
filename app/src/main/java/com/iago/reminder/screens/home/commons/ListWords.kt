package com.iago.reminder.screens.home.commons

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.models.Word
import com.iago.reminder.navigation.Screens
import com.iago.reminder.screens.home.HomeScreenViewModel
import com.iago.reminder.utils.GlobalDialogState

@Composable
fun ListWords(
    data: List<Word>,
    navController: NavHostController,
    search: String,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit,
    context: Context,
    viewModel: HomeScreenViewModel,
    createAlarm: (word: Word, context: Context) -> Unit,
    cancelAlarm: (word: Word, context: Context) -> Unit,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty() && search.isEmpty()) {
        EmptyList(
            onAddWordClick = {
                navController.navigate(Screens.FormScreen.name)
            },
            modifier = modifier
        )
    } else if (search.isNotEmpty() && data.isEmpty()) {
        NoResults(modifier = modifier)
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = data,
                key = { it.id }
            ) { item ->
                WordItem(
                    word = item,
                    context = context,
                    viewModel = viewModel,
                    createAlarm = createAlarm,
                    cancelAlarm = cancelAlarm,
                    onEditClick = { word ->
                        navController.navigate("${Screens.FormScreen.name}?wordId=${word.id}")
                    },
                    onDeleteClick = { word ->
                        openGlobalDialog(
                            GlobalDialogState(
                                messageID = R.string.exclude_word,
                                isConfirmation = true,
                                onSuccess = {
                                    viewModel.deleteWord(context, word, cancelAlarm)
                                }
                            )
                        )
                    }
                )
            }
        }
    }
}
