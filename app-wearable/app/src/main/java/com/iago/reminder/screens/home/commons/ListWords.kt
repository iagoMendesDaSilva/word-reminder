package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import com.iago.reminder.models.WordModel

@Composable
fun ListWords(
    listState: ScalingLazyListState,
    words: List<WordModel>,
    callback: (index: Int) -> Unit
) {

    ScalingLazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        items(words.size) { index ->
            WordItem(words[index], index, callback)
        }
    }
}