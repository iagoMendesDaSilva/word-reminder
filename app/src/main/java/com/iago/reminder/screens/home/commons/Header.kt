package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    onAboutClick: () -> Unit,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }

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

        Box {
            FilledTonalIconButton(
                onClick = { menuExpanded = true },
                modifier = Modifier.size(42.dp),
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = BackgroundDark,
                    contentColor = White
                )
            ) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = stringResource(R.string.settings),
                    modifier = Modifier.size(22.dp)
                )
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                shape = RoundedCornerShape(16.dp),
                containerColor = BackgroundDark
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.import_title),
                            style = MaterialTheme.typography.bodyMedium,
                            color = White
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.FileDownload,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    onClick = {
                        menuExpanded = false
                        openGlobalDialog(
                            GlobalDialogState(
                                messageID = R.string.importWords,
                                isConfirmation = true,
                                icon = Icons.Rounded.FileDownload,
                                iconTint = Primary,
                                onSuccess = { import() }
                            )
                        )
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.export_title),
                            style = MaterialTheme.typography.bodyMedium,
                            color = White
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.FileUpload,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    onClick = {
                        menuExpanded = false
                        openGlobalDialog(
                            GlobalDialogState(
                                messageID = R.string.exportWords,
                                isConfirmation = true,
                                icon = Icons.Rounded.FileUpload,
                                iconTint = Primary,
                                onSuccess = { export() }
                            )
                        )
                    }
                )

                HorizontalDivider(
                    color = White.copy(alpha = 0.12f),
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.about),
                            style = MaterialTheme.typography.bodyMedium,
                            color = White
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Info,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    onClick = {
                        menuExpanded = false
                        onAboutClick()
                    }
                )
            }
        }
    }
}
