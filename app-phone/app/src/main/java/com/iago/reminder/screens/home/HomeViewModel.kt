package com.iago.reminder.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.reminder.database.ReminderDao
import com.iago.reminder.models.Resource
import com.iago.reminder.models.UserModel
import com.iago.reminder.models.WordModel
import com.iago.reminder.repository.UserRepository
import com.iago.reminder.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject
constructor(
    private val repositoryUser: UserRepository,
    private val repositoryWord: WordRepository,
    private val reminderDao: ReminderDao
) : ViewModel() {

    private val _words = MutableStateFlow<List<WordModel>>(emptyList())
    val words = _words.asStateFlow()

    var loading = mutableStateOf(false)
    var loadingWords = mutableStateOf(true)
    var error = mutableStateOf<String?>("")

    private fun startValuesToRequest() {
        loading.value = true
        error.value = ""
    }

    fun getAllWords() {
        viewModelScope.launch {
            val user = reminderDao.getUser()
            when (val response = repositoryWord.getWords(user.token!!)) {
                is Resource.Success -> _words.value = response.data!!
                is Resource.Error -> error.value = response.message
            }
            loadingWords.value = false
        }
    }

    fun editWord(
        word: String,
        wordTranslate: String,
        time: String,
        id: Int,
        active: Boolean,
        callbackSuccess: () -> Unit,
        callbackError: () -> Unit
    ) {
        startValuesToRequest()
        viewModelScope.launch {
            val user = reminderDao.getUser()
            error.value = ""
            when (val response =
                repositoryWord.editWord(user.id, id, word, wordTranslate, time, active, user.token!!)) {
                is Resource.Success -> callbackSuccess()
                is Resource.Error -> {
                    error.value = response.message
                    callbackError()
                }
            }
            loading.value = false
        }
    }

    fun saveWord(word: String, wordTranslate: String, time: String, callback: () -> Unit) {
        startValuesToRequest()
        viewModelScope.launch {
            val user = reminderDao.getUser()
            error.value = ""
            when (val response =
                repositoryWord.saveWord(user.id, word, wordTranslate, time, user.token!!)) {
                is Resource.Success -> callback()
                is Resource.Error -> error.value = response.message
            }
            loading.value = false
        }
    }

    fun logout(callback: () -> Unit) = viewModelScope.launch {
        val user = reminderDao.getUser()
        repositoryUser.logout(user.token!!)
        callback()
    }

    fun clearDB() = viewModelScope.launch {
        reminderDao.deleteUser()
    }

    fun saveOneSignalPlayerID(playerID: String) {
        viewModelScope.launch {
            val user = reminderDao.getUser()
            val response = repositoryUser.savePlayerID(playerID, user.token!!)
        }
    }

    fun removeWord(item: WordModel) {
        startValuesToRequest()
        viewModelScope.launch {
            val newList = _words.value.toMutableList()
            newList.remove(item)

            val user = reminderDao.getUser()
            when (val response = repositoryWord.deleteWord(item.id, user.token!!)) {
                is Resource.Success -> _words.value = newList
                is Resource.Error -> error.value = response.message
            }
            loading.value = false
        }
    }

    fun getToken(callback: (token: String) -> Unit){
        viewModelScope.launch {
            val user = reminderDao.getUser()
            callback(user.token!!)
        }

    }

}