package com.iago.reminder.di

import android.content.Context
import androidx.room.Room
import com.iago.reminder.BuildConfig
import com.iago.reminder.api.ApiDict
import com.iago.reminder.api.ApiWord
import com.iago.reminder.database.ReminderDao
import com.iago.reminder.database.ReminderDatabase
import com.iago.reminder.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): ReminderDatabase =
        Room.databaseBuilder(
            context,
            ReminderDatabase::class.java,
            "reminder_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideReminderDao(reminderDatabase: ReminderDatabase): ReminderDao =
        reminderDatabase.getDao()

    @Singleton
    @Provides
    fun provideApiWord(): ApiWord = Retrofit.Builder()
        .addConverterFactory((GsonConverterFactory.create()))
        .baseUrl(BuildConfig.API_WORD_BASE_URL)
        .build()
        .create(ApiWord::class.java)

    @Singleton
    @Provides
    fun provideApiDict(): ApiDict = Retrofit.Builder()
        .addConverterFactory((GsonConverterFactory.create()))
        .baseUrl(BuildConfig.API_DICT_BASE_URL)
        .build()
        .create(ApiDict::class.java)

    @Singleton
    @Provides
    fun provideRepository(apiWord: ApiWord, apiDict: ApiDict) = Repository(apiWord, apiDict)

}