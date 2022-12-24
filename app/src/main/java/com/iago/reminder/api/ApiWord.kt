package com.iago.reminder.api

import com.iago.reminder.models.ListVocabulary
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface ApiWord {

    @GET("word")
    suspend fun getWordOfTheDay(): ListVocabulary

}