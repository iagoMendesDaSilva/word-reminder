package com.iago.reminder.screens.vocabulary

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iago.reminder.R
import com.iago.reminder.models.Dictionary
import com.iago.reminder.repository.Repository
import com.iago.reminder.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VocabularyScreenViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    var loading = mutableStateOf(true)
    var error = mutableStateOf<Int?>(null)

    private val _word = MutableStateFlow<Dictionary?>(null)
    val word = _word.asStateFlow()

    fun getWord() {
        viewModelScope.launch {
            loading.value = true
            error.value = null
            when (val response = repository.getWord()) {
                is Resource.Success -> getDict(response.data?.get(0) ?: "")
                is Resource.Error -> {
                    error.value = R.string.error_default
                    loading.value = false
                }
            }
        }
    }

    private fun getDict(word: String) {
        viewModelScope.launch {
            when (val response = repository.getDict(word)) {
                is Resource.Success -> _word.value = response.data?.get(0)
                is Resource.Error -> error.value = R.string.error_default
            }
            loading.value = false
        }
    }

}

