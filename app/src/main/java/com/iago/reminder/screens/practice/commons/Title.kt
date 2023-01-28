package com.iago.reminder.screens.practice.commons

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.iago.reminder.R
import com.iago.reminder.helpers.ButtonSpeaker
import com.iago.reminder.models.Word
import com.iago.reminder.ui.theme.White
import com.iago.reminder.utils.PRACTICE_TYPE

@Composable
fun Title(guessTranslate: PRACTICE_TYPE, word: Word) {

    val context = LocalContext.current

    val wordToGuess =
        when (guessTranslate) {
            PRACTICE_TYPE.AUDIO -> word.word_translate
            PRACTICE_TYPE.WORD -> word.word
            PRACTICE_TYPE.AUDIO_TRANSLATE -> word.word
            else -> word.word_translate
        }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                maxLines = 2,
                color = White,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(end = 10.dp),
                text = stringResource(
                    R.string.find_pair,
                    if (guessTranslate != PRACTICE_TYPE.AUDIO_TRANSLATE) wordToGuess else ""
                ),
            )
            if (guessTranslate == PRACTICE_TYPE.AUDIO_TRANSLATE)
                ButtonSpeaker(context = context, word = wordToGuess)

        }
    }
}