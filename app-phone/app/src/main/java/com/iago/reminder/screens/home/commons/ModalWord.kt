package com.iago.reminder.screens.home.commons

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.iago.reminder.ContextProvider
import com.iago.reminder.R
import com.iago.reminder.models.WordModel
import com.iago.reminder.screens.home.HomeViewModel
import com.iago.reminder.ui.theme.BackGround
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.WhiteOpacity

@Composable
fun ModalWord(modalVisible: MutableState<Boolean>, word: MutableState<WordModel?>) {

    val context = LocalContext.current
    val homeViewModel = hiltViewModel<HomeViewModel>()

    val wordInput = remember { mutableStateOf("") }
    val dateInput = remember { mutableStateOf("") }
    val wordTranslateInput = remember { mutableStateOf("") }


    if (word.value != null) {
        wordInput.value = word.value!!.word
        dateInput.value = word.value!!.time
        wordTranslateInput.value = word.value!!.word_translate
    } else {
        wordInput.value = ""
        dateInput.value = ""
        wordTranslateInput.value = ""
    }

    if (modalVisible.value) {
        AlertDialog(
            backgroundColor = BackGround,
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                word.value = null
                modalVisible.value = false
            },
            text = { ContentModal(word, wordInput, wordTranslateInput, dateInput, modalVisible) },
            buttons = {
                Row(
                    modifier = Modifier
                        .padding(bottom = 25.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(.5f),
                        shape = RoundedCornerShape(40.dp),
                        onClick = {
                            verifyDataModal(
                                context,
                                word,
                                homeViewModel,
                                modalVisible,
                                wordInput,
                                wordTranslateInput,
                                dateInput
                            )
                        }
                    ) {
                        if (homeViewModel.loading.value)
                            LoadModal()
                        else
                            Text(if (word.value != null) "Edit" else "Save", color = White)
                    }
                }
            }
        )
    }
}

fun verifyDataModal(
    context: Context,
    word: MutableState<WordModel?>,
    homeViewModel: HomeViewModel,
    modalVisible: MutableState<Boolean>,
    wordInput: MutableState<String>,
    wordTranslateInput: MutableState<String>,
    dateInput: MutableState<String>
) {
    if (wordInput.value.isNullOrEmpty() || wordTranslateInput.value.isNullOrEmpty())
        Toast.makeText(
            context,
             ContextProvider.getContext().getString(R.string.ERROR_EMPTY),
            Toast.LENGTH_SHORT
        )
            .show()
    else
        if (word.value != null)
            homeViewModel.editWord(
                wordInput.value,
                wordTranslateInput.value,
                dateInput.value,
                word.value!!.id,
                word.value!!.active, { refreshScreen(modalVisible, homeViewModel, word) }, {}) else
            homeViewModel.saveWord(
                wordInput.value,
                wordTranslateInput.value,
                dateInput.value
            ) { refreshScreen(modalVisible, homeViewModel, word) }

}

@Composable
fun LoadModal() {
    CircularProgressIndicator(
        color = White,
        strokeWidth = 3.dp,
        modifier = Modifier.size(20.dp),
    )
}

@Composable
fun ContentModal(
    word: MutableState<WordModel?>,
    wordInput: MutableState<String>,
    wordTranslateInput: MutableState<String>,
    dateInput: MutableState<String>,
    modalVisible: MutableState<Boolean>
) {
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                color = WhiteOpacity,
                text = if (word.value != null) stringResource(R.string.LBL_EDIT_WORD) else stringResource(
                    R.string.LBL_NEW_WORD
                ),
            )
            IconButton(onClick = {
                word.value = null
                modalVisible.value = false
            }) {
                Icon(
                    tint = White,
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.DESCRIPTION_ICON_CLOSE),
                )
            }
        }
        InputModal(wordInput.value, stringResource(R.string.LBL_WORD)) {
            wordInput.value = it
        }
        InputModal(wordTranslateInput.value, stringResource(R.string.LBL_TRANSLATE)) {
            wordTranslateInput.value = it
        }
        InputDate(dateInput)
    }
}

fun refreshScreen(
    modalVisible: MutableState<Boolean>,
    homeViewModel: HomeViewModel,
    word: MutableState<WordModel?>
) {
    word.value = null
    modalVisible.value = false
    homeViewModel.getAllWords()
}
