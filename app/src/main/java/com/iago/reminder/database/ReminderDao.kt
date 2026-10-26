package com.iago.reminder.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.iago.reminder.models.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Query("SELECT * FROM words WHERE id = :id")
    suspend fun getWord(id: Int): Word?

    @Query("SELECT * FROM words ORDER BY id DESC")
    suspend fun getWords(): List<Word>

    @Query("SELECT * FROM words ORDER BY id DESC")
    fun getWordsFlow(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWord(word: Word): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editWord(word: Word): Int

    @Delete
    suspend fun deleteWord(word: Word): Int
}
