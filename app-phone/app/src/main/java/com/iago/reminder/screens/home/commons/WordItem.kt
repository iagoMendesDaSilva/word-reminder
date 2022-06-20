package com.iago.reminder.screens.home.commons

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.iago.reminder.R
import com.iago.reminder.models.WordModel
import com.iago.reminder.screens.home.HomeViewModel
import com.iago.reminder.ui.theme.BackGroundDark
import com.iago.reminder.ui.theme.Primary
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.WhiteOpacity
import java.util.*

@Composable
fun WordItem(
    word: WordModel,
    modalVisible: MutableState<Boolean>,
    itemModal: MutableState<WordModel?>,
) {
    val context = LocalContext.current
    val homeViewModel = hiltViewModel<HomeViewModel>()

    val activeWord = remember { mutableStateOf(word.active) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BackGroundDark, shape = RoundedCornerShape(10.dp))
            .padding(10.dp)
            .clickable {
                itemModal.value = word
                modalVisible.value = true
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                tint = White,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable { playWordAudio(context, word.word) },
                imageVector = Icons.Default.VolumeUp,
                contentDescription = stringResource(R.string.DESCRIPTION_ICON_AUDIO),
            )
            Column() {
                Text(text = word.word, color = White, modifier = Modifier.padding(bottom = 5.dp))
                Text(text = word.word_translate, color = WhiteOpacity)
            }
        }
        Column() {
            Text(
                text = word.time.substring(0, 5),
                color = White,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Switch(
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    uncheckedThumbColor = Color.White,
                    checkedTrackColor = Primary,
                    uncheckedTrackColor = WhiteOpacity
                ),
                checked = activeWord.value,
                onCheckedChange = { changeActiveWord(activeWord, it, homeViewModel, word) }
            )
        }
    }
}

fun changeActiveWord(
    activeWord: MutableState<Boolean>,
    switchState: Boolean,
    homeViewModel: HomeViewModel,
    word: WordModel,
) {
    activeWord.value = switchState
    homeViewModel.editWord(word.word, word.word_translate, word.time, word.id, switchState,
        { homeViewModel.getAllWords() },
        { activeWord.value = !switchState }
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
