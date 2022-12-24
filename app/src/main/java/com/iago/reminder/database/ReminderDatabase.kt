package com.iago.reminder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iago.reminder.models.Word

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class ReminderDatabase : RoomDatabase() {

    abstract fun getDao(): ReminderDao

}