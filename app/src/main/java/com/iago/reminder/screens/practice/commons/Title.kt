package com.iago.reminder.screens.practice.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.iago.reminder.R
import com.iago.reminder.helpers.ButtonSpeaker
import com.iago.reminder.models.Word
import com.iago.reminder.ui.theme.Primary
import com.iago.reminder.ui.theme.White
import com.iago.reminder.utils.PRACTICE_TYPE
import com.iago.reminder.utils.Speaker

@Composable
fun Title(
    guessTranslate: PRACTICE_TYPE,
    word: Word,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val wordToGuess = when (guessTranslate) {
        PRACTICE_TYPE.AUDIO -> word.word_translate
        PRACTICE_TYPE.WORD -> word.word
        PRACTICE_TYPE.AUDIO_TRANSLATE -> word.word
        else -> word.word_translate
    }

    LaunchedEffect(guessTranslate, word) {
        if (guessTranslate == PRACTICE_TYPE.AUDIO_TRANSLATE || guessTranslate == PRACTICE_TYPE.AUDIO) {
            Speaker.playWordAudio(context, word.word)
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        val promptText = if (guessTranslate == PRACTICE_TYPE.AUDIO_TRANSLATE) {
            "Listen to the word"
        } else {
            stringResource(R.string.find_pair, wordToGuess)
        }

        Text(
            text = promptText,
            style = MaterialTheme.typography.headlineLarge,
            color = White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        if (guessTranslate == PRACTICE_TYPE.AUDIO_TRANSLATE || guessTranslate == PRACTICE_TYPE.AUDIO) {
            Spacer(modifier = Modifier.width(12.dp))
            ButtonSpeaker(
                context = context,
                word = word.word,
                size = 46.dp,
                backgroundColor = Primary,
                iconColor = White
            )
        }
    }
}
