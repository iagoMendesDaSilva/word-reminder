package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iago.reminder.models.Word
import com.iago.reminder.screens.home.HomeScreenViewModel

@Composable
fun WordItem(
    word: Word,
    viewModel: HomeScreenViewModel,
    createAlarm: (word: Word) -> Unit,
    cancelAlarm: (word: Word) -> Unit,
) {

    val openDialog = remember { mutableStateOf(false) }
    val active = remember { mutableStateOf(word.active) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (!viewModel.loadingDelete.value)
                    openDialog.value = true
            }
            .background(MaterialTheme.colors.onBackground, shape = MaterialTheme.shapes.small)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        WordAndTranslateColumn(word)
        TimeAndSwitchColumn(word, viewModel, active,cancelAlarm,createAlarm)
    }

    if (openDialog.value)
        DialogWord(word, openDialog)
}

