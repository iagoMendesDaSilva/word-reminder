package com.iago.reminder.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class WordModel(
    var id: Int,
    var time: String,
    var word: String,
    var word_translate: String,
    var active: Boolean,
    var id_user: Int,
)