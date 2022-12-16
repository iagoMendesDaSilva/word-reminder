package com.iago.reminder.helpers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.WhiteOpacity

@Composable
fun InputDefault(
    value: String,
    label: String,
    icon: ImageVector,
    password: Boolean,
    iconDescription: String,
    keyboardType: KeyboardType= KeyboardType.Text ,
    capitalization: KeyboardCapitalization =KeyboardCapitalization.None,
    callback: (text: String) -> Unit,
) {

    val showPassword = remember { mutableStateOf(false) }

    OutlinedTextField(
        singleLine = true,
        value = value,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, capitalization = capitalization),
        modifier = Modifier.fillMaxWidth(.9f),
        visualTransformation =
        if (password && !showPassword.value)
            PasswordVisualTransformation()
        else VisualTransformation.None,
        onValueChange = { callback(it) },
        shape = MaterialTheme.shapes.large,
        label = { Text(label, color = White, style = MaterialTheme.typography.body1) },
        leadingIcon = {
            Icon(
                tint = White,
                imageVector = icon,
                contentDescription = iconDescription,
            )
        },
        trailingIcon = {
            if (password)
                Icon(
                    contentDescription = iconDescription,
                    tint = if (showPassword.value) White else WhiteOpacity,
                    modifier = Modifier.clickable { showPassword.value = !showPassword.value },
                    imageVector = if (showPassword.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = White,
            focusedBorderColor = White,
            unfocusedBorderColor = White,
        )
    )
}