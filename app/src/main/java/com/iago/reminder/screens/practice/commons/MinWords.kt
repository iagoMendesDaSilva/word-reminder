package com.iago.reminder.screens.practice.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iago.reminder.R
import com.iago.reminder.helpers.MeditationImage
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.WhiteOpacity

@Composable
fun MinWords() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        MeditationImage()
        Text(
            color = White,
            text = stringResource(R.string.unavailable),
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            color = WhiteOpacity,
            style = MaterialTheme.typography.body1,
            text = stringResource(R.string.min_words),
        )
    }
}