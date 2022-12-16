package com.iago.reminder.database

import androidx.room.*
import com.iago.reminder.models.WordModel

@Dao
interface ReminderDao {

    @Query("SELECT * FROM words WHERE id=:id")
    suspend fun getWord(id: Int): WordModel

    @Query("SELECT * FROM words ORDER BY active DESC")
    suspend fun getWords(): List<WordModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWord(word: WordModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editWord(word: WordModel)

    @Delete
    suspend fun deleteWord(word: WordModel)

}