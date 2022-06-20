package com.iago.reminder.utils

import com.iago.reminder.R

interface ErrorService {

    fun getErrorMessage(statusCode: Int, messageFail: Int): Int {

        return when (statusCode) {
            404 -> messageFail
            401 -> R.string.error_session
            500 -> R.string.error_default
            else -> R.string.error_default
        }
    }
}