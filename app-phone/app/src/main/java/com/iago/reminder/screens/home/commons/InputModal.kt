package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.White

@Composable
fun InputModal(value: String, label: String, callback: (text: String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(.95f),
        value = value,
        onValueChange = { callback(it) },
        shape = RoundedCornerShape(100.dp),
        label = { Text(label, color = White) },
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = White,
            focusedBorderColor = White,
            unfocusedBorderColor = White
        )
    )
}