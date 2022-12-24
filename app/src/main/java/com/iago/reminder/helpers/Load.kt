package com.iago.reminder.helpers

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.White

@Composable
fun Load(strokeWidth: Dp = 2.dp) {
    CircularProgressIndicator(
        color = White,
        strokeWidth = strokeWidth,
    )
}