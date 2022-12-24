package com.iago.reminder.api

import com.iago.reminder.models.ListDictionary
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ApiDict {

    @GET("en/{word}")
    suspend fun getWord(@Path("word") word: String): ListDictionary

}