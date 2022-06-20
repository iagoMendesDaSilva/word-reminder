package com.iago.reminder.screens.login.commons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PanoramaFishEye
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.WhiteOpacity

@Composable
fun Input(
    value: String,
    label: String,
    icon: ImageVector,
    password: Boolean,
    iconDescription: String,
    callback: (text: String) -> Unit
) {

    val showPassword = remember { mutableStateOf(false) }

    OutlinedTextField(
        singleLine=true,
        value = value,
        modifier = Modifier
            .padding(0.dp, 5.dp)
            .fillMaxWidth(.9f),
        visualTransformation = if (password && !showPassword.value)
            PasswordVisualTransformation()
        else VisualTransformation.None,
        onValueChange = { callback(it) },
        shape = RoundedCornerShape(100.dp),
        label = { Text(label, color = White) },
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
                    imageVector = Icons.Default.RemoveRedEye,
                    tint = if(showPassword.value) White else WhiteOpacity,
                    modifier = Modifier.clickable { showPassword.value = !showPassword.value },
                )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = White,
            focusedBorderColor = White,
            unfocusedBorderColor = White,
        )
    )
}