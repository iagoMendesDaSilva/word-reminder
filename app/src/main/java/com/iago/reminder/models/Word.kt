package com.iago.reminder.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var time: String,
    var word: String,
    var word_translate: String,
    var active: Boolean = true
)
