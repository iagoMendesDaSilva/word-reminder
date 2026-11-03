package com.iago.reminder.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class GlobalDialogState(
    val messageID: Int,
    val error: Boolean = false,
    val isConfirmation: Boolean = false,
    val icon: ImageVector? = null,
    val iconTint: Color? = null,
    val title: String? = null,
    val onSuccess: (() -> Unit)? = null,
    val onDismiss: () -> Unit = {}
)
