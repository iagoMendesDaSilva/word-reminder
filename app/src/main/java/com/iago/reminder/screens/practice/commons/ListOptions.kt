package com.iago.reminder.screens.practice.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.iago.reminder.models.Word
import com.iago.reminder.ui.theme.*

@Composable
fun ListOptions(
    guessTranslate: Boolean,
    word: Word,
    wordPressed: MutableState<Int?>,
    options: List<Word>
) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        options.forEachIndexed { index, option ->
            val color =
                getColor(option.word_translate, word.word_translate, wordPressed.value, options)

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp)
                    .background(
                        color,
                        MaterialTheme.shapes.small,
                    )
                    .padding(15.dp)
                    .clickable {
                        wordPressed.value = index
                    }) {
                Text(
                    maxLines = 1,
                    color = White,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2,
                    text = if (guessTranslate) option.word_translate else option.word,
                )
            }
        }
    }
}

fun getColor(option: String, wordTranslate: String, wordPressed: Int?, words: List<Word>): Color {
    var color = BackGroundDark

    if (wordPressed != null) {
        color = if (option === wordTranslate)
            Success
        else if (words[wordPressed].word_translate == option && wordTranslate != option)
            Error
        else
            Gray
    }
    return color
}
