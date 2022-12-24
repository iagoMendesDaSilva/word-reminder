package com.iago.reminder.screens.vocabulary.commons

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.iago.reminder.ui.theme.White

@Composable
fun Definition(definition: String){
    Text(
        color = White,
        text = definition,
        style = MaterialTheme.typography.body1,
    )
}