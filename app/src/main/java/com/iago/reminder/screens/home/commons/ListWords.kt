package com.iago.reminder.screens.home.commons

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.models.Word
import com.iago.reminder.navigation.Screens
import com.iago.reminder.screens.home.HomeScreenViewModel
import com.iago.reminder.utils.GlobalDialogState
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListWords(
    data: List<Word>,
    navController: NavHostController,
    search: String,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit,
    context: Context,
    viewModel: HomeScreenViewModel,
    createAlarm: (word: Word,context: Context) -> Unit,
    cancelAlarm:  (word: Word,context: Context) -> Unit,
) {
    if (data.isEmpty() && search.isEmpty())
        EmptyList()
    else if (search.isNotEmpty() && data.isEmpty())
        NoResults()
    LazyColumn() {
        itemsIndexed(items = data) { _, item ->
            RevealSwipe(
                enableSwipe = !viewModel.loadingDelete.value,
                shape = MaterialTheme.shapes.small,
                directions = setOf(RevealDirection.StartToEnd, RevealDirection.EndToStart),
                onBackgroundEndClick = {
                    openGlobalDialog(
                        GlobalDialogState(
                            messageID = R.string.exclude_word,
                            imageIconTwoOptions = R.drawable.icon_warning,
                            onDismiss = {}, onSuccess = {
                                viewModel.deleteWord(context, item, cancelAlarm)
                            })
                    )
                },
                onBackgroundStartClick = {
                    navController.currentBackStackEntry?.arguments?.putParcelable("word", item)
                    navController.navigate(Screens.FormScreen.name)
                },
                hiddenContentEnd = { WordItemSwipedEnd(item.id) },
                hiddenContentStart = { WordItemSwipedStart(item.id) },
            ) {
                WordItem(item, context, viewModel, createAlarm, cancelAlarm)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}