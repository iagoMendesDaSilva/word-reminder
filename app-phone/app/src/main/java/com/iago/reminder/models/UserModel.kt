package com.iago.reminder.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val token: String?,
    val email: String,
    var password: String?,
    val onesignal_playerID: String?,
)