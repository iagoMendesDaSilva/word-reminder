package com.iago.reminder.helpers

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.HelpOutline
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.iago.reminder.R
import com.iago.reminder.ui.theme.BackgroundDark
import com.iago.reminder.ui.theme.Error
import com.iago.reminder.ui.theme.Primary
import com.iago.reminder.ui.theme.Success
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.White70
import com.iago.reminder.utils.GlobalDialogState

@Composable
fun GlobalDialog(
    dialogState: GlobalDialogState
) {
    val icon = when {
        dialogState.isConfirmation -> Icons.AutoMirrored.Rounded.HelpOutline
        dialogState.error -> Icons.Rounded.ErrorOutline
        else -> Icons.Rounded.CheckCircle
    }

    val iconTint = when {
        dialogState.isConfirmation -> Primary
        dialogState.error -> Error
        else -> Success
    }

    val defaultTitle = when {
        dialogState.isConfirmation -> stringResource(R.string.app_name)
        dialogState.error -> stringResource(R.string.oops)
        else -> stringResource(R.string.success)
    }

    AlertDialog(
        onDismissRequest = dialogState.onDismiss,
        shape = RoundedCornerShape(24.dp),
        containerColor = BackgroundDark,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(44.dp)
            )
        },
        title = {
            Text(
                text = dialogState.title ?: defaultTitle,
                style = MaterialTheme.typography.titleLarge,
                color = White,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = stringResource(dialogState.messageID),
                style = MaterialTheme.typography.bodyLarge,
                color = White70,
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    dialogState.onSuccess?.invoke()
                    dialogState.onDismiss()
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (dialogState.error) Error else Primary,
                    contentColor = White
                )
            ) {
                Text(
                    text = if (dialogState.isConfirmation) stringResource(R.string.button_confirm)
                    else stringResource(R.string.ok),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        dismissButton = if (dialogState.isConfirmation) {
            {
                OutlinedButton(
                    onClick = dialogState.onDismiss,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = White70
                    )
                ) {
                    Text(
                        text = stringResource(R.string.button_cancel),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        } else null
    )
}
