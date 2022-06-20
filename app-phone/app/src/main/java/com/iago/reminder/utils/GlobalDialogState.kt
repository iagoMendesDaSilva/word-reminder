package com.iago.reminder.utils


data class GlobalDialogState(
    val messageID: Int,
    val error: Boolean = true,
    val imageIconTwoOptions: Int? = null,
    val onSuccess: (() -> Unit)? = null,
    val onDismiss: () -> Unit,
)