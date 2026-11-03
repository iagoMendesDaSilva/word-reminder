package com.lighthouse.wordreminder.helpers

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.WarningAmber
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
import com.lighthouse.wordreminder.R
import com.lighthouse.wordreminder.ui.theme.BackgroundDark
import com.lighthouse.wordreminder.ui.theme.Error
import com.lighthouse.wordreminder.ui.theme.Primary
import com.lighthouse.wordreminder.ui.theme.Success
import com.lighthouse.wordreminder.ui.theme.White
import com.lighthouse.wordreminder.ui.theme.White70
import com.lighthouse.wordreminder.utils.GlobalDialogState

@Composable
fun GlobalDialog(
    dialogState: GlobalDialogState
) {
    val icon = dialogState.icon ?: when {
        dialogState.isConfirmation -> Icons.Rounded.WarningAmber
        dialogState.error -> Icons.Rounded.ErrorOutline
        else -> Icons.Rounded.CheckCircle
    }

    val iconTint = dialogState.iconTint ?: when {
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
                    containerColor = if (dialogState.error || (dialogState.iconTint == Error)) Error else Primary,
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
