package com.iago.reminder.utils

data class GlobalDialogState(
    val messageID: Int,
    val error: Boolean = false,
    val isConfirmation: Boolean = false,
    val title: String? = null,
    val onSuccess: (() -> Unit)? = null,
    val onDismiss: () -> Unit = {}
)
