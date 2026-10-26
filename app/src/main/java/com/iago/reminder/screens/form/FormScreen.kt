package com.iago.reminder.screens.form

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Spellcheck
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.helpers.ButtonDefault
import com.iago.reminder.helpers.InputDefault
import com.iago.reminder.models.Word
import com.iago.reminder.navigation.Screens
import com.iago.reminder.screens.form.commons.TimeAndActiveContainer
import com.iago.reminder.ui.theme.Background
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.White70
import com.iago.reminder.utils.GlobalDialogState
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun FormScreen(
    navController: NavHostController,
    wordId: Int?,
    createAlarm: (word: Word, context: Context) -> Unit,
    cancelAlarm: (word: Word, context: Context) -> Unit,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit,
    viewModel: FormScreenViewModel = viewModel()
) {
    val context = LocalContext.current

    val word = remember { mutableStateOf("") }
    val translate = remember { mutableStateOf("") }
    val time = remember { mutableStateOf(getDefaultTime()) }
    val remind = remember { mutableStateOf(true) }

    LaunchedEffect(wordId) {
        if (wordId != null && wordId > 0) {
            viewModel.loadWord(wordId)
        }
    }

    LaunchedEffect(viewModel.wordLoaded.value) {
        viewModel.wordLoaded.value?.let { loaded ->
            word.value = loaded.word
            translate.value = loaded.word_translate
            time.value = formatInitialTime(loaded.time)
            remind.value = loaded.active
        }
    }

    if (viewModel.error.value != null) {
        openGlobalDialog(
            GlobalDialogState(
                messageID = viewModel.error.value!!,
                error = true,
                onDismiss = { viewModel.error.value = null }
            )
        )
    }

    val isEditing = wordId != null && wordId > 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 20.dp)
            .padding(top = 28.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isEditing) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            Column {
                Text(
                    text = if (isEditing) stringResource(R.string.edit_word)
                    else stringResource(R.string.new_word),
                    style = MaterialTheme.typography.headlineLarge,
                    color = White
                )
                Text(
                    text = if (remind.value) stringResource(R.string.remind_desc, time.value.take(5))
                    else stringResource(R.string.not_remind_desc),
                    style = MaterialTheme.typography.bodyMedium,
                    color = White70,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Center: Inputs and Time/Active container centered vertically
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputDefault(
                value = word.value,
                label = stringResource(R.string.word),
                icon = Icons.Rounded.Spellcheck,
                capitalization = KeyboardCapitalization.Words,
                callback = { word.value = it }
            )

            Spacer(modifier = Modifier.height(14.dp))

            InputDefault(
                value = translate.value,
                label = stringResource(R.string.translate),
                icon = Icons.Rounded.Language,
                capitalization = KeyboardCapitalization.Sentences,
                callback = { translate.value = it }
            )

            Spacer(modifier = Modifier.height(14.dp))

            TimeAndActiveContainer(remind = remind, time = time)
        }

        // Bottom Action Button
        ButtonDefault(
            text = if (isEditing) stringResource(R.string.button_edit)
            else stringResource(R.string.button_save),
            loading = viewModel.loading.value,
            onClick = {
                if (word.value.isBlank() || translate.value.isBlank()) {
                    openGlobalDialog(
                        GlobalDialogState(
                            messageID = R.string.error_empty_word,
                            error = true
                        )
                    )
                    return@ButtonDefault
                }

                if (isEditing) {
                    viewModel.editWord(
                        context = context,
                        id = wordId,
                        word = word.value,
                        wordTranslate = translate.value,
                        time = time.value.take(5),
                        active = remind.value,
                        createAlarm = createAlarm,
                        cancelAlarm = cancelAlarm,
                        callback = {
                            openGlobalDialog(
                                GlobalDialogState(
                                    messageID = R.string.word_edited,
                                    error = false,
                                    onDismiss = {
                                        navController.popBackStack(Screens.HomeScreen.name, false)
                                    }
                                )
                            )
                        }
                    )
                } else {
                    viewModel.saveWord(
                        context = context,
                        word = word.value,
                        wordTranslate = translate.value,
                        time = time.value.take(5),
                        active = remind.value,
                        createAlarm = createAlarm,
                        callback = {
                            openGlobalDialog(
                                GlobalDialogState(
                                    messageID = R.string.word_saved,
                                    error = false,
                                    onDismiss = {
                                        word.value = ""
                                        translate.value = ""
                                        remind.value = true
                                    }
                                )
                            )
                        }
                    )
                }
            }
        )
    }
}

private fun getDefaultTime(): String {
    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("HH:mm aa", Locale.US)
    return sdf.format(calendar.time)
}

private fun formatInitialTime(timeStr: String?): String {
    if (timeStr.isNullOrEmpty()) return getDefaultTime()
    return try {
        val parts = timeStr.split(":")
        val hour = parts.getOrNull(0)?.toIntOrNull() ?: 8
        val min = parts.getOrNull(1)?.take(2)?.toIntOrNull() ?: 30
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, min)
            set(Calendar.SECOND, 0)
        }
        val sdf = SimpleDateFormat("HH:mm aa", Locale.US)
        sdf.format(cal.time)
    } catch (_: Exception) {
        getDefaultTime()
    }
}
