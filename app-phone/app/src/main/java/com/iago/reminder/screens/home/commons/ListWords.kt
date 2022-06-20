package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.iago.reminder.R
import com.iago.reminder.models.WordModel
import com.iago.reminder.screens.home.HomeViewModel
import com.iago.reminder.ui.theme.Primary
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.WhiteOpacity
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListWords(
    words: List<WordModel>,
    modalVisible: MutableState<Boolean>,
    itemModal: MutableState<WordModel?>
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()

    if (homeViewModel.loadingWords.value)
        Load()

    if (!homeViewModel.loadingWords.value && words.isEmpty())
        EmptyComponent()
    else
        LazyColumn(
            Modifier
                .padding(5.dp)
                .fillMaxHeight()
        ) {
            itemsIndexed(items = words) { index, item ->
                RevealSwipe(
                    shape = RoundedCornerShape(10.dp),
                    directions = setOf(RevealDirection.EndToStart),
                    onBackgroundEndClick = {
                        if (!homeViewModel.loading.value)
                            homeViewModel.removeWord(item)
                    },
                    hiddenContentEnd = { DeleteCard() }
                ) {
                    WordItem(item, modalVisible, itemModal)
                }
                Spacer(modifier = Modifier.height(if (words.size == index + 1) 65.dp else 10.dp))
            }
        }
}

@Composable
fun DeleteCard() {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Primary)
            .padding(10.dp)
    ) {
        Icon(
            tint = White,
            imageVector = Icons.Default.Delete,
            modifier = Modifier.align(Alignment.CenterEnd),
            contentDescription = stringResource(R.string.DESCRIPTION_ICON_DELETE),
        )
    }
}

@Composable
fun Load() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.95f),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = White,
            strokeWidth = 3.dp,
            modifier = Modifier.size(25.dp),
        )
    }
}

@Composable
fun EmptyComponent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        Text(buildAnnotatedString {
            append(stringResource(R.string.EMOJI_SAD))
            withStyle(style = SpanStyle(color = WhiteOpacity)) {
                append(" ${stringResource(R.string.LBL_EMPTY_LIST)}")
            }
        }
        )
    }
}