package com.iago.reminder.helpers

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.WhiteOpacityPlus
import com.iago.reminder.utils.Speaker

@Composable
fun ButtonSpeaker(context: Context, word: String){
    Box(
        modifier = Modifier
            .width(30.dp)
            .height(30.dp)
            .background(
                color = WhiteOpacityPlus,
                shape = MaterialTheme.shapes.large,
            )
            .clickable {
                Speaker.playWordAudio(context, word)
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