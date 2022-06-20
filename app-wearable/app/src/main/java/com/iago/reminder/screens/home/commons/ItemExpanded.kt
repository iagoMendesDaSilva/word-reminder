package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.SwipeToDismissBox
import androidx.wear.compose.material.Text
import com.iago.reminder.models.WordModel
import com.iago.reminder.theme.WhiteOpacity

@Composable
fun ItemExpanded(words: List<WordModel>, indexItem: MutableState<Int?>) {

    val item = words[indexItem.value!!]

    SwipeToDismissBox(
        onDismissed = { indexItem.value = null },
    ) { isBackground ->
        if (isBackground) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.onBackground)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .background(MaterialTheme.colors.background),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        maxLines = 1,
                        text = item.word,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body2,
                    )
                    Text(
                        maxLines = 1,
                        color = WhiteOpacity,
                        text = item.word_translate,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }
    }
}