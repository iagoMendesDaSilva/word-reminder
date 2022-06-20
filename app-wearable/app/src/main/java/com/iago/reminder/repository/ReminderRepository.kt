package com.iago.reminder.repository

import com.iago.reminder.R
import com.iago.reminder.ContextProvider
import com.iago.reminder.api.ReminderApi
import com.iago.reminder.models.Resource
import com.iago.reminder.models.WordModel
import com.iago.reminder.utils.ErrorService
import retrofit2.HttpException
import javax.inject.Inject

class ReminderRepository @Inject constructor(private val api: ReminderApi) : ErrorService {

    suspend fun getWords(token: String): Resource<List<WordModel>> {
        val response = try {
            api.getWords(token)
        } catch (e: HttpException) {
            return Resource.Error(
                getErrorMessage(
                    e.code(),
                    ContextProvider.getContext().getString(R.string.ERROR_DEFAULT)
                )
            )
        } catch (e: Exception) {
            return Resource.Error(ContextProvider.getContext().getString(R.string.ERROR_DEFAULT))
        }
        return Resource.Success(response)
    }
}