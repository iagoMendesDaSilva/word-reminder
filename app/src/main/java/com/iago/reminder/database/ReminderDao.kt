package com.iago.reminder.database

import androidx.room.*
import com.iago.reminder.models.Word

@Dao
interface ReminderDao {

    @Query("SELECT * FROM words WHERE id=:id")
    suspend fun getWord(id: Int): Word

    @Query("SELECT * FROM words ORDER BY active DESC")
    suspend fun getWords(): List<Word>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWord(word: Word)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

}