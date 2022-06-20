package com.iago.reminder.screens.verificationCode.commons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.White


@Composable
fun InputCode(
    value: String,
    callback: (text: String) -> Unit,
) {

    OutlinedTextField(
        singleLine = true,
        value = value,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Number,
        ),
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .width(50.dp)
            .height(50.dp),
        onValueChange = { if (it.length <= 1) callback(it) },
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = White,
            focusedBorderColor = White,
            unfocusedBorderColor = White,
        )
    )
}