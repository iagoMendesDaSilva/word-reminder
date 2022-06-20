package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator

@Composable
fun Load() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            indicatorColor = White,
            strokeWidth = 3.dp,
            modifier = Modifier.size(25.dp),
        )
    }
}