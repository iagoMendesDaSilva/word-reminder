package com.iago.reminder.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.reminder.R
import com.iago.reminder.database.ReminderDao
import com.iago.reminder.models.WordModel
import com.iago.reminder.repository.UserRepository
import com.iago.reminder.repository.WordRepository
import com.iago.reminder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val wordRepository: WordRepository,
    private val reminderDao: ReminderDao
) : ViewModel() {

    var loading = mutableStateOf(true)
    var loadingDelete = mutableStateOf(false)
    var error = mutableStateOf<Int?>(null)

    private val _words = MutableStateFlow<List<WordModel>>(emptyList())
    val words = _words.asStateFlow()

    fun getWords() {
        viewModelScope.launch {
            loading.value = true
            error.value = null

            val token = getToken()
            if (token != null)
                when (val response = wordRepository.getWords(token)) {
                    is Resource.Success -> _words.value =
                        organizeListByActive(response.data!!.reversed())
                    else -> error.value = response.message
                }
            else
                error.value = R.string.error_default

            loading.value = false
        }
    }

    fun deleteWord(item: WordModel) {
        viewModelScope.launch {
            loadingDelete.value = true
            error.value = null

            val token = getToken()

            if (token != null)
                when (val response = wordRepository.deleteWord(item.id, token)) {
                    is Resource.Success -> {
                        val newList = _words.value.toMutableList()
                        newList.remove(item)
                        _words.value = newList
                    }
                    else -> error.value = response.message
                }
            else
                error.value = R.string.error_default

            loadingDelete.value = false
        }
    }

    fun logout(callback: () -> Unit) = viewModelScope.launch {
        val token = getToken()
        if (token != null)
            userRepository.logout(token)
        callback()
    }

    fun clearDB() = viewModelScope.launch {
        reminderDao.deleteUser()
    }

    suspend fun getToken(): String? {
        val user = reminderDao.getUser()
        return if (user != null)
            user.token
        else
            null
    }

    fun saveOneSignalPlayerID(playerID: String) {
        viewModelScope.launch {
            val token = getToken()
            if (token != null)
                userRepository.savePlayerID(playerID, token)
        }
    }

    private fun organizeListByActive(data: List<WordModel>): List<WordModel> {
        val active = data.filter { it.active }
        val notActive = data.filter { !it.active }

        val list = mutableListOf<WordModel>()
        list.addAll(active)
        list.addAll(notActive)

        return list
    }

    fun editActiveWord(word: WordModel, onError: () -> Unit) {
        viewModelScope.launch {
            val token = getToken()
            if (token != null) {
                when (val response = wordRepository.editWord(
                    word.id,
                    word.word,
                    word.word_translate,
                    word.time,
                    !word.active,
                    word.id_user,
                    token
                )) {
                    is Resource.Success -> {
                        val newList = _words.value.toMutableList()
                        newList.find { it.id == word.id }?.active = !word.active
                        _words.value = newList
                    }
                    is Resource.Error -> {
                        error.value = response.message
                        onError()
                    }
                }
            } else
                error.value = R.string.error_default
        }
    }

    fun unregister(callback: () -> Unit) = viewModelScope.launch {
        val token = getToken()
        if (token != null)
            when (val response = userRepository.unregister(token)) {
                is Resource.Success -> callback()
                is Resource.Error -> error.value = response.message
            }
        else
            error.value = R.string.error_default
    }
}

