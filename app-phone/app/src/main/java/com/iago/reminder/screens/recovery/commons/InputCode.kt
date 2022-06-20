package com.iago.reminder.screens.recovery.commons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.BackGroundDark
import com.iago.reminder.ui.theme.White

@Composable
fun InputCode(
    value: String,
    callback: (text: String) -> Unit
) {
    TextField(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .width(50.dp),
        value = value,
        singleLine = true,
        onValueChange = { if (it.length <= 1) callback(it) },
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = White,
            backgroundColor = BackGroundDark,
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}