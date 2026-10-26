package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FileDownload
import androidx.compose.material.icons.rounded.FileUpload
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iago.reminder.R
import com.iago.reminder.ui.theme.BackgroundDark
import com.iago.reminder.ui.theme.Primary
import com.iago.reminder.ui.theme.White
import com.iago.reminder.utils.GlobalDialogState

@Composable
fun Header(
    wordCount: Int,
    activeCount: Int,
    import: () -> Unit,
    export: () -> Unit,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = stringResource(R.string.my_words),
                style = MaterialTheme.typography.headlineLarge,
                color = White
            )
            if (wordCount > 0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = White.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = stringResource(R.string.total_words, wordCount),
                            style = MaterialTheme.typography.labelSmall,
                            color = White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Primary.copy(alpha = 0.3f)
                    ) {
                        Text(
                            text = stringResource(R.string.active_reminders, activeCount),
                            style = MaterialTheme.typography.labelSmall,
                            color = White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Export button
            FilledTonalIconButton(
                onClick = {
                    openGlobalDialog(
                        GlobalDialogState(
                            messageID = R.string.exportWords,
                            isConfirmation = true,
                            onSuccess = { export() }
                        )
                    )
                },
                modifier = Modifier.size(42.dp),
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = BackgroundDark,
                    contentColor = White
                )
            ) {
                Icon(
                    imageVector = Icons.Rounded.FileUpload,
                    contentDescription = "Export words",
                    modifier = Modifier.size(22.dp)
                )
            }

            // Import button
            FilledTonalIconButton(
                onClick = {
                    openGlobalDialog(
                        GlobalDialogState(
                            messageID = R.string.importWords,
                            isConfirmation = true,
                            onSuccess = { import() }
                        )
                    )
                },
                modifier = Modifier.size(42.dp),
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = BackgroundDark,
                    contentColor = White
                )
            ) {
                Icon(
                    imageVector = Icons.Rounded.FileDownload,
                    contentDescription = "Import words",
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}
