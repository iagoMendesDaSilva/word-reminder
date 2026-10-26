package com.iago.reminder.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

object Speaker {
    private var tts: TextToSpeech? = null
    private var isInitialized = false

    fun init(context: Context) {
        if (tts == null) {
            tts = TextToSpeech(context.applicationContext) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    tts?.language = Locale.ENGLISH
                    isInitialized = true
                }
            }
        }
    }

    fun playWordAudio(context: Context, word: String) {
        if (tts != null && isInitialized) {
            tts?.speak(word, TextToSpeech.QUEUE_FLUSH, null, "WordReminder_${System.currentTimeMillis()}")
        } else {
            tts = TextToSpeech(context.applicationContext) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    tts?.language = Locale.ENGLISH
                    isInitialized = true
                    tts?.speak(word, TextToSpeech.QUEUE_FLUSH, null, "WordReminder_${System.currentTimeMillis()}")
                }
            }
        }
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        isInitialized = false
    }
}
