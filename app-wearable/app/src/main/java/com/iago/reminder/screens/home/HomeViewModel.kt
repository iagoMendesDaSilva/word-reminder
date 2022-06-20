package com.iago.reminder.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.reminder.models.Resource
import com.iago.reminder.models.WordModel
import com.iago.reminder.repository.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject
constructor(
    private val repository: ReminderRepository,
) : ViewModel() {

    private val _words = MutableStateFlow<List<WordModel>>(emptyList())
    val words = _words.asStateFlow()

    var loading = mutableStateOf(false)
    var error = mutableStateOf<String?>("")

    private fun startValuesToRequest() {
        loading.value = true
        error.value = ""
    }

    fun getAllWords(token: String) {
        viewModelScope.launch {
            startValuesToRequest()
            when (val response = repository.getWords(token)) {
                is Resource.Success -> _words.value = response.data!!
                is Resource.Error -> error.value = response.message
            }
            loading.value = false
        }
    }


}