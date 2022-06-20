package com.iago.reminder.api

import com.iago.reminder.models.*
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface ReminderApi {

    @GET("words")
    suspend fun getWords(@Header("Authorization") token: String): List<WordModel>

}