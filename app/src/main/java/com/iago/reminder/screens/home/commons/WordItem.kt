package com.iago.reminder.screens.home.commons

import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iago.reminder.R
import com.iago.reminder.helpers.SwitchDefault
import com.iago.reminder.models.Word
import com.iago.reminder.screens.home.HomeScreenViewModel
import com.iago.reminder.ui.theme.BackgroundDark
import com.iago.reminder.ui.theme.BlueInfo
import com.iago.reminder.ui.theme.Error
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.White10
import com.iago.reminder.ui.theme.White70
import com.iago.reminder.utils.Speaker

@Composable
fun WordItem(
    word: Word,
    context: Context,
    viewModel: HomeScreenViewModel,
    createAlarm: (word: Word, context: Context) -> Unit,
    cancelAlarm: (word: Word, context: Context) -> Unit,
    onEditClick: (word: Word) -> Unit,
    onDeleteClick: (word: Word) -> Unit,
    modifier: Modifier = Modifier
) {
    val openDialog = remember { mutableStateOf(false) }
    val activeState = remember { mutableStateOf(word.active) }

    LaunchedEffect(word.active) {
        activeState.value = word.active
    }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onDeleteClick(word)
                    false
                }
                SwipeToDismissBoxValue.StartToEnd -> {
                    onEditClick(word)
                    false
                }
                SwipeToDismissBoxValue.Settled -> false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier.clip(RoundedCornerShape(20.dp)),
        backgroundContent = {
            val direction = dismissState.dismissDirection
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> Error
                    SwipeToDismissBoxValue.StartToEnd -> BlueInfo
                    else -> Color.Transparent
                },
                animationSpec = tween(durationMillis = 280, easing = FastOutSlowInEasing),
                label = "DismissColor"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 24.dp),
                contentAlignment = if (direction == SwipeToDismissBoxValue.StartToEnd)
                    Alignment.CenterStart else Alignment.CenterEnd
            ) {
                if (direction == SwipeToDismissBoxValue.StartToEnd) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = stringResource(R.string.edit),
                        tint = White,
                        modifier = Modifier.size(30.dp)
                    )
                } else if (direction == SwipeToDismissBoxValue.EndToStart) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = stringResource(R.string.delete),
                        tint = White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundDark),
            border = BorderStroke(1.dp, White10),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (!viewModel.loadingDelete.value) {
                        Speaker.playWordAudio(context, word.word)
                        openDialog.value = true
                    }
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Word and Translation Column
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = word.word,
                        style = MaterialTheme.typography.titleLarge,
                        color = White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = word.word_translate,
                        style = MaterialTheme.typography.bodyLarge,
                        color = White70,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Time and Switch Column (time above switch, slightly larger, aligned to right, without icon or background)
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = word.time.take(5),
                        fontSize = 17.sp,
                        style = MaterialTheme.typography.titleMedium,
                        color = White,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    SwitchDefault(initialValue = activeState.value) { checked ->
                        activeState.value = checked
                        viewModel.editActiveWord(
                            word = word,
                            context = context,
                            createAlarm = createAlarm,
                            cancelAlarm = cancelAlarm,
                            onError = { activeState.value = word.active }
                        )
                    }
                }
            }
        }
    }

    if (openDialog.value) {
        DialogWord(word = word, openDialog = openDialog)
    }
}
