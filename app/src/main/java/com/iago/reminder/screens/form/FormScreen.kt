package com.iago.reminder.screens.form

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.helpers.ButtonDefault
import com.iago.reminder.helpers.InputDefault
import com.iago.reminder.models.Word
import com.iago.reminder.navigation.Screens
import com.iago.reminder.screens.form.commons.TimeAndActiveContainer
import com.iago.reminder.ui.theme.White
import com.iago.reminder.utils.GlobalDialogState
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FormScreen(
    navController: NavHostController,
    wordToEdit: Word?,
    createAlarm: (word: Word, context: Context) -> Unit,
    cancelAlarm: (word: Word, context: Context) -> Unit,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {

    var word = remember { mutableStateOf(wordToEdit?.word ?: "") }
    var time = remember { mutableStateOf(getInitialTime(wordToEdit?.time)) }
    var reminde = remember { mutableStateOf(wordToEdit?.active ?: true) }
    var translate = remember { mutableStateOf(wordToEdit?.word_translate ?: "") }

    val context = LocalContext.current
    val viewModel = hiltViewModel<FormScreenViewModel>()

    if (viewModel.error.value != null)
        openGlobalDialog(GlobalDialogState(viewModel.error.value!!) {
            viewModel.error.value = null
        })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TitleContainer(time.value, wordToEdit?.id, reminde.value)
        Column(verticalArrangement = Arrangement.Center) {
            InputDefault(
                value = word.value,
                label = stringResource(R.string.word),
                icon = Icons.Default.TextFields,
                password = false,
                iconDescription = "",
                keyboardType = KeyboardType.Email,
                capitalization = KeyboardCapitalization.Words,
                callback = { word.value = it }
            )
            Spacer(modifier = Modifier.height(10.dp))
            InputDefault(
                value = translate.value,
                label = stringResource(R.string.translate),
                icon = Icons.Default.Public,
                password = false,
                iconDescription = "",
                keyboardType = KeyboardType.Email,
                capitalization = KeyboardCapitalization.Words,
                callback = { translate.value = it }
            )
            Spacer(modifier = Modifier.height(10.dp))
            TimeAndActiveContainer(reminde, time)
        }
        ButtonDefault(
            if (wordToEdit != null) stringResource(R.string.button_edit)
            else stringResource(R.string.button_save),
            viewModel.loading.value
        ) {
            onPressButton(
                context,
                wordToEdit?.id,
                word,
                translate,
                reminde,
                time.value,
                viewModel,
                openGlobalDialog,
                navController,
                createAlarm,
                cancelAlarm
            )
        }
    }
}

@Composable
fun TitleContainer(time: String, id: Int?, reminde: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = if (id != null) stringResource(R.string.edit_word)
            else stringResource(R.string.new_word),
            color = White,
            style = MaterialTheme.typography.h1
        )
        Text(
            text = if (reminde) stringResource(R.string.remind_desc, time)
            else stringResource(R.string.not_remind_desc),
            color = White,
            style = MaterialTheme.typography.body1
        )
    }
}

fun getInitialTime(time: String?): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.SECOND, 0)

    val simpleDateFormat = SimpleDateFormat("HH:mm aa");

    if (!time.isNullOrEmpty())
        calendar.time = SimpleDateFormat("HH:mm").parse(time);

    return simpleDateFormat.format(calendar.time)

}

fun onPressButton(
    context: Context,
    id: Int?,
    word: MutableState<String>,
    translate: MutableState<String>,
    reminde: MutableState<Boolean>,
    time: String,
    viewModel: FormScreenViewModel,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit,
    navController: NavHostController,
    createAlarm: (word: Word, context: Context) -> Unit,
    cancelAlarm: (word: Word, context: Context) -> Unit,
) {
    if (word.value.isEmpty() || translate.value.isEmpty()) {
        viewModel.error.value = R.string.error_empty_word
        return
    }
    if (id != null)
        viewModel.editWord(
            context,
            id,
            word.value,
            translate.value,
            time.substring(0, 5),
            reminde.value,
            createAlarm,
            cancelAlarm
        ) {
            openGlobalDialog(GlobalDialogState(R.string.word_edited, false) {
                navController.navigate(Screens.HomeScreen.name, null)
            })
        }
    else
        viewModel.saveWord(
            context,
            word.value,
            translate.value,
            time.substring(0, 5),
            reminde.value,
            createAlarm
        ) {
            openGlobalDialog(GlobalDialogState(R.string.word_saved, false) {
                resetFields(word, translate, reminde)
            })
        }
}

fun resetFields(
    word: MutableState<String>,
    translate: MutableState<String>,
    remind: MutableState<Boolean>
) {
    word.value = ""
    translate.value = ""
    remind.value = true
}