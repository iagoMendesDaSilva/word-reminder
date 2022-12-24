package com.iago.reminder.models

data class Definition(
    val definition: String,
    val synonyms: List<String>?,
    val antonyms: List<String>?
)