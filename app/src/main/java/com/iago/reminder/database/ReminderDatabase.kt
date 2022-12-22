package com.iago.reminder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iago.reminder.models.WordModel

@Database(entities = [WordModel::class], version = 1, exportSchema = false)
abstract class ReminderDatabase : RoomDatabase() {

    abstract fun getDao(): ReminderDao

}