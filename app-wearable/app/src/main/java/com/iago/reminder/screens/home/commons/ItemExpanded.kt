package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.SwipeToDismissBox
import androidx.wear.compose.material.Text
import com.iago.reminder.models.WordModel
import com.iago.reminder.theme.BackGround
import com.iago.reminder.theme.BackGroundDark
import com.iago.reminder.theme.WhiteOpacity

@Composable
fun ItemExpanded(words: List<WordModel>, indexItem: MutableState<Int?>) {

    val item = words[indexItem.value!!]

    SwipeToDismissBox(
        onDismissed = { indexItem.value = null },
    ) { isBackground ->
        if (isBackground) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackGroundDark)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackGround),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(item.word, color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Text(item.word_translate, color = WhiteOpacity, fontSize = 25.sp)
            }
        }
    }
}