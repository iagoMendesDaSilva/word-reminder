package com.iago.reminder.helpers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.White

@Composable
fun OutlinedButtonDefault(text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = { onClick() },
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(2.dp, White),
        modifier = Modifier.fillMaxWidth(.9f),
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
    ) {
        Text(text, color = White, style = MaterialTheme.typography.button)
    }
}
