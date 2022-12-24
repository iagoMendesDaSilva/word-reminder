package com.iago.reminder.repository

import com.iago.reminder.R
import com.iago.reminder.api.ApiDict
import com.iago.reminder.api.ApiWord
import com.iago.reminder.models.ListDictionary
import com.iago.reminder.models.ListVocabulary
import com.iago.reminder.utils.Resource
import retrofit2.HttpException
import javax.inject.Inject

class Repository @Inject constructor(private val apiWord: ApiWord, private val apiDict: ApiDict) {

    suspend fun getWord(): Resource<ListVocabulary> {
        val response = try {
            apiWord.getWordOfTheDay()
        } catch (e: HttpException) {
            return Resource.Error(R.string.error_default)
        } catch (e: Exception) {
            return Resource.Error(R.string.error_default)
        }
        return Resource.Success(response)
    }

    suspend fun getDict(word: String): Resource<ListDictionary> {
        val response = try {
            apiDict.getWord(word)
        } catch (e: HttpException) {
            return Resource.Error(R.string.error_default)
        } catch (e: Exception) {
            return Resource.Error(R.string.error_default)
        }
        return Resource.Success(response)
    }

}