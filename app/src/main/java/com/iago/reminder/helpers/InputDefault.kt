package com.iago.reminder.helpers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.BackgroundDark
import com.iago.reminder.ui.theme.Primary
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.White10
import com.iago.reminder.ui.theme.White70

@Composable
fun InputDefault(
    value: String,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    password: Boolean = false,
    iconDescription: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    callback: (text: String) -> Unit,
) {
    val showPassword = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = callback,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = capitalization
        ),
        modifier = modifier.fillMaxWidth(),
        visualTransformation = if (password && !showPassword.value)
            PasswordVisualTransformation()
        else VisualTransformation.None,
        shape = RoundedCornerShape(32.dp),
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = iconDescription,
                tint = Primary
            )
        },
        trailingIcon = {
            if (password) {
                Icon(
                    imageVector = if (showPassword.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (showPassword.value) "Hide" else "Show",
                    tint = White70,
                    modifier = Modifier.clickable { showPassword.value = !showPassword.value }
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = White,
            unfocusedTextColor = White,
            focusedContainerColor = BackgroundDark,
            unfocusedContainerColor = BackgroundDark,
            focusedBorderColor = Primary,
            unfocusedBorderColor = White10,
            focusedLabelColor = Primary,
            unfocusedLabelColor = White70,
            cursorColor = Primary
        )
    )
}
