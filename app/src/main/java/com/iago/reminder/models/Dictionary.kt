package com.iago.reminder.models

data class Dictionary(
    val license: License,
    val meanings: List<Meaning>,
    val phonetic: String?,
    val phonetics: List<Phonetic>?,
    val sourceUrls: List<String>,
    val word: String
)