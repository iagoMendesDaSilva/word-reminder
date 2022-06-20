package com.iago.reminder.database

import androidx.room.*
import com.iago.reminder.models.UserModel
import com.iago.reminder.models.WordModel

@Dao
interface ReminderDao {

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getUser(): UserModel

    @Query("SELECT EXISTS(SELECT * FROM users)")
    suspend fun existUser(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editUser(user: UserModel)

    @Query("DELETE FROM users")
    suspend fun deleteUser()

}