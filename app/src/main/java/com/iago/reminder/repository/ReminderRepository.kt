package com.iago.reminder.repository

import com.iago.reminder.database.ReminderDao
import com.iago.reminder.models.Word
import javax.inject.Inject

class ReminderRepository @Inject constructor(
    private val reminderDao: ReminderDao
) {

    suspend fun getWord(id: Int): Word {
        return reminderDao.getWord(id)
    }

    suspend fun getWords(): List<Word> {
        return reminderDao.getWords()
    }

    suspend fun addWord(word: Word) {
        return reminderDao.addWord(word)
    }

    suspend fun editWord(word: Word) {
        return reminderDao.editWord(word)
    }

    suspend fun deleteWord(word: Word) {
        return reminderDao.deleteWord(word)
    }
}
