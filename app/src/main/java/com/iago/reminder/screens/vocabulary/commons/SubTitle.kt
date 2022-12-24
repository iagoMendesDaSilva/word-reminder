package com.iago.reminder.screens.vocabulary.commons

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.White

@Composable
fun SubTitle(subtitle: String){
    Text(
        color = White,
        text = subtitle,
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.padding(bottom = 8.dp, top = 8.dp),
    )
}