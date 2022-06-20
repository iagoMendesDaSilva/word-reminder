package com.iago.reminder.repository

import android.content.res.Resources
import com.iago.reminder.ContextProvider
import com.iago.reminder.R
import com.iago.reminder.api.ReminderApi
import com.iago.reminder.models.*
import com.iago.reminder.utils.ErrorService
import retrofit2.HttpException
import javax.inject.Inject

class WordRepository @Inject constructor(private val api: ReminderApi) : ErrorService {


    suspend fun getWords(token: String): Resource<List<WordModel>> {
        val response = try {
            api.getWords(token)
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(),  ContextProvider.getContext().getString(R.string.ERROR_DEFAULT)))
        } catch (e: Exception) {
            return Resource.Error( ContextProvider.getContext().getString(R.string.ERROR_DEFAULT))
        }
        return Resource.Success(response)
    }

    suspend fun editWord(
        idUser: Int,
        id: Int,
        word: String,
        wordTranslate: String,
        time: String,
        active: Boolean,
        token: String
    ): Resource<WordModel> {
        val wordModel = WordModel(
            id = id,
            word = word,
            time = time,
            id_user = idUser,
            word_translate = wordTranslate,
            active = active,
        )
        val response = try {
            api.editWord(wordModel, id, token)
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(),  ContextProvider.getContext().getString(R.string.ERROR_DEFAULT)))
        } catch (e: Exception) {
            return Resource.Error( ContextProvider.getContext().getString(R.string.ERROR_DEFAULT))
        }
        return Resource.Success(response)
    }

    suspend fun saveWord(
        idUser: Int,
        word: String,
        wordTranslate: String,
        time: String,
        token: String
    ): Resource<WordModel> {
        val wordModel = WordModel(
            id = 0,
            word = word,
            time = time,
            active = true,
            id_user = idUser,
            word_translate = wordTranslate,
        )
        val response = try {
            api.saveWord(wordModel, token)
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(),  ContextProvider.getContext().getString(R.string.ERROR_DEFAULT)))
        } catch (e: Exception) {
            return Resource.Error( ContextProvider.getContext().getString(R.string.ERROR_DEFAULT))
        }
        return Resource.Success(response)
    }

    suspend fun deleteWord(id: Int, token: String): Resource<SimpleResponseModel> {
        val response = try {
            api.deleteWord(id, token)
        } catch (e: HttpException) {
            return Resource.Error(getErrorMessage(e.code(),  ContextProvider.getContext().getString(R.string.ERROR_DEFAULT)))
        } catch (e: Exception) {
            return Resource.Error( ContextProvider.getContext().getString(R.string.ERROR_DEFAULT))
        }
        return Resource.Success(response)
    }

}
