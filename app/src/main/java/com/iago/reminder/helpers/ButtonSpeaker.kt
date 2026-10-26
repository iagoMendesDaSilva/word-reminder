package com.iago.reminder.helpers

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.White
import com.iago.reminder.utils.Speaker

@Composable
fun ButtonSpeaker(
    context: Context,
    word: String,
    modifier: Modifier = Modifier,
    size: Dp = 38.dp,
    backgroundColor: Color = White.copy(alpha = 0.15f),
    iconColor: Color = White
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable {
                Speaker.playWordAudio(context, word)
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.VolumeUp,
            contentDescription = "Pronounce $word",
            tint = iconColor,
            modifier = Modifier.size(size * 0.55f)
        )
    }
}
