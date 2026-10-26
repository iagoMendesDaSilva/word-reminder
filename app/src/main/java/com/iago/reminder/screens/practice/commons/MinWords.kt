package com.iago.reminder.screens.practice.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.iago.reminder.R
import com.iago.reminder.helpers.ButtonDefault
import com.iago.reminder.helpers.MeditationImage
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.White70

@Composable
fun MinWords(
    onAddWordClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MeditationImage()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.unavailable),
            style = MaterialTheme.typography.headlineMedium,
            color = White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = stringResource(R.string.min_words),
            style = MaterialTheme.typography.bodyMedium,
            color = White70,
            textAlign = TextAlign.Center
        )
        if (onAddWordClick != null) {
            Spacer(modifier = Modifier.height(24.dp))
            ButtonDefault(
                text = stringResource(R.string.new_word),
                onClick = onAddWordClick,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}
