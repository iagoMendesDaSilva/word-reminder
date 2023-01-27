package com.iago.reminder.screens.practice

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.helpers.ButtonDefault
import com.iago.reminder.models.Word
import com.iago.reminder.screens.practice.commons.ListOptions
import com.iago.reminder.screens.practice.commons.MinWords
import com.iago.reminder.screens.practice.commons.Title
import com.iago.reminder.utils.GlobalDialogState
import kotlin.random.Random

@Composable
fun PracticeScreen(
    navController: NavHostController,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit
) {
    BackHandler(enabled = true) {}
    val viewModel = hiltViewModel<PracticeScreenViewModel>()
    val words = viewModel.words.collectAsState().value

    var guessTranslate = remember { mutableStateOf(false) }
    var wordPressed = remember { mutableStateOf<Int?>(null) }
    var wordIndex = remember { mutableStateOf(if (words.size > 2) Random.nextInt(words.size) else 0) }

    LaunchedEffect(key1 = true) {
        viewModel.getWords()
    }

    if (words.size < 2)
        MinWords()
    else {
        Column(
            Modifier
                .fillMaxSize()
                .padding(25.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Title(guessTranslate.value, words[wordIndex.value])
            ListOptions(
                guessTranslate.value,
                words[wordIndex.value],
                wordPressed,
                getOptions(words, words[wordIndex.value])
            )
            ButtonDefault(stringResource(R.string.button_next)) {
                wordPressed.value = null
                wordIndex.value = Random.nextInt(words.size)
                guessTranslate.value = Random.nextBoolean()
            }
        }
    }
}

fun getOptions(words: List<Word>, word: Word): List<Word> {
    val options = words.filterNot { it.word_translate == word.word_translate }
        .shuffled()
        .take(2)  + word
    return options.toMutableList().apply { shuffle(Random(System.currentTimeMillis())) }
}






