package com.iago.reminder.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

object Speaker {
    fun playWordAudio(context: Context, word: String) {
        var speaker: TextToSpeech? = null;

        speaker = TextToSpeech(context) {
            if (it != TextToSpeech.ERROR) {
                speaker?.language = Locale.ENGLISH
                speaker?.speak(word, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }
}