package com.iago.reminder.screens.vocabulary.commons

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iago.reminder.helpers.ButtonSpeaker
import com.iago.reminder.ui.theme.Primary
import com.iago.reminder.ui.theme.White

@Composable
fun Header(word: String, context: Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Primary, shape = MaterialTheme.shapes.small)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            color = White,
            text = word.capitalize(),
            style = MaterialTheme.typography.h1,
        )
        ButtonSpeaker(context, word)
    }
}