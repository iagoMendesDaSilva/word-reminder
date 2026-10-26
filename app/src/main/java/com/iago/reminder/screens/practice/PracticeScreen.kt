package com.iago.reminder.screens.practice

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.iago.reminder.R
import com.iago.reminder.helpers.ButtonDefault
import com.iago.reminder.helpers.Load
import com.iago.reminder.models.Word
import com.iago.reminder.navigation.Screens
import com.iago.reminder.screens.practice.commons.ListOptions
import com.iago.reminder.screens.practice.commons.MinWords
import com.iago.reminder.screens.practice.commons.Title
import com.iago.reminder.ui.theme.Background
import com.iago.reminder.utils.GlobalDialogState
import com.iago.reminder.utils.PRACTICE_TYPE
import kotlin.random.Random

@Composable
fun PracticeScreen(
    navController: NavHostController,
    openGlobalDialog: (dialog: GlobalDialogState) -> Unit,
    viewModel: PracticeScreenViewModel = viewModel()
) {
    BackHandler(enabled = true) {}

    val words by viewModel.words.collectAsState()

    val practiceType = remember { mutableStateOf(PRACTICE_TYPE.WORD) }
    val wordPressed = remember { mutableStateOf<Int?>(null) }
    val wordIndex = remember { mutableIntStateOf(0) }
    val currentOptions = remember { mutableStateOf<List<Word>>(emptyList()) }

    LaunchedEffect(Unit) {
        viewModel.getWords()
    }

    LaunchedEffect(words) {
        if (words.size >= 3) {
            val randomIdx = Random.nextInt(words.size)
            wordIndex.intValue = randomIdx
            val correctWord = words[randomIdx]
            currentOptions.value = getPracticeOptions(words, correctWord)
            practiceType.value = PRACTICE_TYPE.entries.toTypedArray().random()
        }
    }

    if (viewModel.loading.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background),
            contentAlignment = Alignment.Center
        ) {
            Load()
        }
    } else if (words.size < 3) {
        MinWords(
            onAddWordClick = { navController.navigate(Screens.FormScreen.name) },
            modifier = Modifier.background(Background)
        )
    } else {
        val safeIndex = wordIndex.intValue.coerceIn(0, words.size - 1)
        val correctWord = words[safeIndex]
        val options = if (currentOptions.value.isNotEmpty()) currentOptions.value
        else getPracticeOptions(words, correctWord)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(horizontal = 20.dp)
                .padding(top = 28.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title(
                guessTranslate = practiceType.value,
                word = correctWord
            )

            ListOptions(
                guessTranslate = practiceType.value,
                correctWord = correctWord,
                wordPressed = wordPressed,
                options = options
            )

            ButtonDefault(
                text = stringResource(R.string.button_next),
                enabled = wordPressed.value != null,
                onClick = {
                    wordPressed.value = null
                    var nextIndex = Random.nextInt(words.size)
                    if (words.size > 1 && nextIndex == wordIndex.intValue) {
                        nextIndex = (nextIndex + 1) % words.size
                    }
                    wordIndex.intValue = nextIndex
                    val nextWord = words[nextIndex]
                    currentOptions.value = getPracticeOptions(words, nextWord)
                    practiceType.value = PRACTICE_TYPE.entries.toTypedArray().random()
                }
            )
        }
    }
}

private fun getPracticeOptions(words: List<Word>, currentWord: Word): List<Word> {
    val otherWords = words.filterNot { it.id == currentWord.id }
        .shuffled()
        .take(2)
    val list = (otherWords + currentWord).shuffled(Random(System.currentTimeMillis()))
    return list
}
