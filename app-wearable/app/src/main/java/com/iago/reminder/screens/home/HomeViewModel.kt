package com.iago.reminder.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.reminder.R
import com.iago.reminder.models.WordModel
import com.iago.reminder.repository.ReminderRepository
import com.iago.reminder.utils.Resource
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
    var error = mutableStateOf<Int?>(null)

    private fun startValuesToRequest() {
        loading.value = true
        error.value = null
    }

    fun getAllWords(token: String) {
        viewModelScope.launch {
            startValuesToRequest()
            when (val response = repository.getWords(token)) {
                is Resource.Success -> _words.value =
                    organizeListByActive(response.data!!.reversed())
                is Resource.Error -> error.value = R.string.error_default
            }
            loading.value = false
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


}