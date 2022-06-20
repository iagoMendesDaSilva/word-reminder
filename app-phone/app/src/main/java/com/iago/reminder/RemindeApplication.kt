package com.iago.reminder

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ReminderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ContextProvider.setContext(applicationContext);
    }
}

abstract class ContextProvider {

    companion object {

        private lateinit var context: Context

        fun setContext(con: Context) {
            context = con
        }

        fun getContext(): Context {
            return context
        }
    }
}