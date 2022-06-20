package com.iago.reminder.models

data class WordModel(
    var id: Int,
    var time: String,
    var word: String,
    var word_translate: String,
    var active: Boolean,
    var id_user: Int,
)