package com.iago.reminder.repository

import com.iago.reminder.R
import com.iago.reminder.api.ReminderApi
import com.iago.reminder.models.LoginModel
import com.iago.reminder.models.SimpleResponseModel
import com.iago.reminder.models.UserModel
import com.iago.reminder.models.WordModel
import com.iago.reminder.utils.ErrorService
import com.iago.reminder.utils.Resource
import retrofit2.HttpException
import javax.inject.Inject

class WordRepository @Inject constructor(private val api: ReminderApi) : ErrorService {

    suspend fun getWords(token: String): Resource<List<WordModel>> {
        val response = try {
            api.getWords(token)
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(), R.string.error_default))
        } catch (e: Exception) {
            return Resource.Error(R.string.error_default)
        }
        return Resource.Success(response)
    }

    suspend fun deleteWord(id: Int, token: String): Resource<SimpleResponseModel> {
        val response = try {
            api.deleteWord(id, token)
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(), R.string.error_default))
        } catch (e: Exception) {
            return Resource.Error(R.string.error_default)
        }
        return Resource.Success(response)
    }

    suspend fun saveWord(
        word: String,
        wordTranslate: String,
        time: String,
        active: Boolean,
        id: Int,
        token: String
    ): Resource<WordModel> {
        val response = try {
            api.saveWord(
                WordModel(0, time, word, wordTranslate, active, id),
                token
            )
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(), R.string.error_default))
        } catch (e: Exception) {
            return Resource.Error(R.string.error_default)
        }
        return Resource.Success(response)
    }

    suspend fun editWord(
        id:Int,
        word: String,
        wordTranslate: String,
        time: String,
        active: Boolean,
        idUser: Int,
        token: String
    ): Resource<WordModel> {
        val response = try {
            api.editWord(
                WordModel(id, time, word, wordTranslate, active, idUser),
                id,
                token
            )
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(), R.string.error_default))
        } catch (e: Exception) {
            return Resource.Error(R.string.error_default)
        }
        return Resource.Success(response)
    }

}
