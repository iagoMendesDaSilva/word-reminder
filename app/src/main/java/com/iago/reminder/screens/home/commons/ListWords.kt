package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit,
    viewModel: HomeScreenViewModel,
    createAlarm: (word: Word) -> Unit,
    cancelAlarm: (word: Word) -> Unit
) {
    if (data.isEmpty())
        EmptyList()
    else
        LazyColumn() {
            itemsIndexed(items = data) { _, item ->
                RevealSwipe(
                    enableSwipe=!viewModel.loadingDelete.value,
                    shape = MaterialTheme.shapes.small,
                    directions = setOf(RevealDirection.StartToEnd, RevealDirection.EndToStart),
                    onBackgroundEndClick = {
                        openGlobalDialog(
                            GlobalDialogState(
                                messageID = R.string.exclude_word,
                                imageIconTwoOptions = R.drawable.icon_warning,
                                onDismiss = {}, onSuccess = {
                                    viewModel.deleteWord(item, cancelAlarm)
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
                    WordItem(item,viewModel,createAlarm,cancelAlarm)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
}