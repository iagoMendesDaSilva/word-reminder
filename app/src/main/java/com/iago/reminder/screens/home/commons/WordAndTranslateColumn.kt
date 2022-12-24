package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iago.reminder.models.Word
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.WhiteOpacity

@Composable
fun WordAndTranslateColumn(word: Word) {
    Column(
        modifier = Modifier.fillMaxWidth(.8f),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = word.word, maxLines = 1, color = White, style = MaterialTheme.typography.body2)
        Text(
            maxLines = 1,
            color = WhiteOpacity,
            text = word.word_translate,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 5.dp)
        )
    }
}
