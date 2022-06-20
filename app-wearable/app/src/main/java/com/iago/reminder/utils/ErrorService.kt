package com.iago.reminder.utils

import com.iago.reminder.R
import com.iago.reminder.ContextProvider

interface ErrorService {

    fun getErrorMessage(statusCode: Int, messageFail: String): String {

        return when (statusCode) {
            404 -> messageFail
            500 -> ContextProvider.getContext().getString(R.string.ERROR_DEFAULT)
            else -> ContextProvider.getContext().getString(R.string.ERROR_DEFAULT)
        }
    }
}