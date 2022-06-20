package com.iago.reminder.api

import com.iago.reminder.models.*
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface ReminderApi {

    @POST("auth/login")
    suspend fun login(@Body body: LoginModel): UserModel

    @GET("auth/logout")
    suspend fun logout(@Header("Authorization") token: String): SimpleResponseModel

    @POST("auth/verify-email")
    suspend fun verifyEmail(@Body body: EmailModel): IdModel

    @POST("auth/register")
    suspend fun register(@Body body: UserModel): SimpleResponseModel

    @POST("auth/confirm-email")
    suspend fun confirmEmail(@Body emailModel: EmailModel): IdModel

    @POST("/auth/confirm-verification-code/{id}")
    suspend fun confirmCode(@Path("id") id: Int, @Body code: CodeModel): TokenModel

    @POST("/auth/recovery")
    suspend fun recovery( @Header("Authorization") token: String, @Body passwordModel: PasswordModel): UserModel

    @POST("word")
    suspend fun saveWord(@Body body: WordModel, @Header("Authorization") token: String): WordModel

    @GET("words")
    suspend fun getWords(@Header("Authorization") token: String): List<WordModel>

    @PUT("word/{id}")
    suspend fun editWord(@Body body: WordModel, @Path("id") id: Int, @Header("Authorization") token: String): WordModel

    @DELETE("word/{id}")
    suspend fun deleteWord(@Path("id") id: Int, @Header("Authorization") token: String): SimpleResponseModel

    @PUT("user/onesignal-player-id")
    suspend fun playerID(@Body body: PlayerIdModel, @Header("Authorization") token: String): SimpleResponseModel

}