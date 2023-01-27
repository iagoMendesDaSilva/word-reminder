package com.iago.reminder.screens.practice.commons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.iago.reminder.R
import com.iago.reminder.models.Word
import com.iago.reminder.ui.theme.White

@Composable
fun Title(guessTranslate: Boolean, word: Word) {
    val wordToGuess = if (guessTranslate) word.word else word.word_translate
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            maxLines = 2,
            color = White,
            style = MaterialTheme.typography.h1,
            overflow = TextOverflow.Ellipsis,
            text = stringResource(R.string.find_pair, wordToGuess),
        )
    }
}