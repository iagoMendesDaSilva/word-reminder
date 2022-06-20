package com.iago.reminder.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class WordModel(
    var id: Int,
    var time: String,
    var word: String,
    var word_translate: String,
    var active: Boolean,
    var id_user: Int,
) : Parcelable