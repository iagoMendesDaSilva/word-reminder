package com.lighthouse.wordreminder.screens.home.commons

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
import com.lighthouse.wordreminder.R
import com.lighthouse.wordreminder.helpers.MeditationImage
import com.lighthouse.wordreminder.ui.theme.White
import com.lighthouse.wordreminder.ui.theme.White70

@Composable
fun EmptyList(
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
            text = stringResource(R.string.no_words),
            style = MaterialTheme.typography.headlineMedium,
            color = White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = stringResource(R.string.first_word),
            style = MaterialTheme.typography.bodyMedium,
            color = White70,
            textAlign = TextAlign.Center
        )
    }
}
