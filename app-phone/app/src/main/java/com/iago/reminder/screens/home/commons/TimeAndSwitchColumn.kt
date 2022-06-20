package com.iago.reminder.screens.home.commons

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iago.reminder.helpers.SwitchDefault
import com.iago.reminder.models.WordModel
import com.iago.reminder.screens.home.HomeScreenViewModel
import com.iago.reminder.ui.theme.White

@Composable
fun TimeAndSwitchColumn(
    word: WordModel,
    viewModel: HomeScreenViewModel,
    active: MutableState<Boolean>,
) {

    LaunchedEffect(key1 = active.value) {
        if (word.active != active.value)
            viewModel.editActiveWord(word){
                active.value = word.active
            }
    }

    Column(horizontalAlignment = Alignment.End) {
        Text(
            color = White,
            text = word.time.substring(0, 5),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        SwitchDefault(initialValue = active.value) {
            active.value = it

        }
    }
}