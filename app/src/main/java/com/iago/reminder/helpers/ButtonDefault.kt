package com.iago.reminder.helpers

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.iago.reminder.ui.theme.White

@Composable
fun ButtonDefault(text: String, loading: Boolean = false, onClick: () -> Unit) {

    val focusManager = LocalFocusManager.current

    Button(
        enabled = !loading,
        onClick = {
            focusManager.clearFocus()
            onClick()
                  },
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(.9f),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            disabledBackgroundColor = MaterialTheme.colors.primary,
        ),
    ) {
        if (loading)
            CircularProgressIndicator(
                color = White,
                strokeWidth = 2.dp,
                modifier = Modifier.size(15.dp)
            )
        else
            Text(text, color = White, style = MaterialTheme.typography.button)
    }
}