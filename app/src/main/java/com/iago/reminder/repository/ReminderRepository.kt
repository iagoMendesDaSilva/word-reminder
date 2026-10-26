package com.iago.reminder.repository

import com.iago.reminder.database.ReminderDao
import com.iago.reminder.models.Word
import kotlinx.coroutines.flow.Flow

class ReminderRepository(
    private val reminderDao: ReminderDao
) {
    suspend fun getWord(id: Int): Word? = reminderDao.getWord(id)

    suspend fun getWords(): List<Word> = reminderDao.getWords()

    fun getWordsFlow(): Flow<List<Word>> = reminderDao.getWordsFlow()

    suspend fun addWord(word: Word): Long = reminderDao.addWord(word)

    suspend fun editWord(word: Word): Int = reminderDao.editWord(word)

    suspend fun deleteWord(word: Word): Int = reminderDao.deleteWord(word)
}
