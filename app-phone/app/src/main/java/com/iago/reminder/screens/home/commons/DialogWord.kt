package com.iago.reminder.screens.home.commons

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.iago.reminder.models.WordModel
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.WhiteOpacity
import com.iago.reminder.ui.theme.WhiteOpacityPlus
import java.util.*

@Composable
fun DialogWord(word: WordModel, openDialog: MutableState<Boolean>) {

    val context = LocalContext.current

    Dialog(
        onDismissRequest = { openDialog.value = false },
        content = {
            Column(
                modifier = Modifier
                    .width(280.dp)
                    .height(300.dp)
                    .background(
                        color = MaterialTheme.colors.background,
                        shape = RoundedCornerShape(30.dp),
                    ).padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                Text(
                    maxLines=1,
                    color = White,
                    text = word.word,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h1
                )
                Text(
                    maxLines=1,
                    color = WhiteOpacity,
                    text = word.word_translate,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h2,
                )
            }
            Box(
                modifier = Modifier
                    .width(280.dp)
                    .height(300.dp)
                    .padding(20.dp), contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                        .background(
                            color = WhiteOpacityPlus,
                            shape = MaterialTheme.shapes.large,
                        )
                        .clickable {
                            playWordAudio(context, word.word)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = null,
                        tint = White
                    )
                }
            }
        },
    )
}

fun playWordAudio(context: Context, word: String) {
    var speaker: TextToSpeech? = null;

    speaker = TextToSpeech(context) {
        if (it != TextToSpeech.ERROR) {
            speaker?.language = Locale.ENGLISH
            speaker?.speak(word, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }
}