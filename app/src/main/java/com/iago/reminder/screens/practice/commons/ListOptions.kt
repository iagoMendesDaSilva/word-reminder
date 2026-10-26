package com.iago.reminder.screens.practice.commons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.iago.reminder.helpers.ButtonSpeaker
import com.iago.reminder.models.Word
import com.iago.reminder.ui.theme.BackgroundDark
import com.iago.reminder.ui.theme.Error
import com.iago.reminder.ui.theme.Primary
import com.iago.reminder.ui.theme.Success
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.White10
import com.iago.reminder.ui.theme.White50
import com.iago.reminder.utils.PRACTICE_TYPE
import com.iago.reminder.utils.Speaker

@Composable
fun ListOptions(
    guessTranslate: PRACTICE_TYPE,
    correctWord: Word,
    wordPressed: MutableState<Int?>,
    options: List<Word>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = wordPressed.value == index
            val hasAnswered = wordPressed.value != null
            val isCorrect = option.id == correctWord.id

            val targetContainerColor = when {
                !hasAnswered -> BackgroundDark
                isCorrect -> Success.copy(alpha = 0.25f)
                isSelected && !isCorrect -> Error.copy(alpha = 0.25f)
                else -> BackgroundDark.copy(alpha = 0.5f)
            }

            val targetBorderColor = when {
                !hasAnswered -> White10
                isCorrect -> Success
                isSelected && !isCorrect -> Error
                else -> White10.copy(alpha = 0.3f)
            }

            val containerColor by animateColorAsState(
                targetValue = targetContainerColor,
                animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing),
                label = "OptionColor"
            )
            val borderColor by animateColorAsState(
                targetValue = targetBorderColor,
                animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing),
                label = "OptionBorder"
            )

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = containerColor),
                border = BorderStroke(1.5.dp, borderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (wordPressed.value == null) {
                            wordPressed.value = index
                            Speaker.playWordAudio(context, option.word)
                        }
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (guessTranslate == PRACTICE_TYPE.AUDIO) {
                            ButtonSpeaker(
                                context = context,
                                word = option.word,
                                size = 42.dp,
                                backgroundColor = Primary.copy(alpha = 0.25f),
                                iconColor = White
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                        }

                        val displayText = when (guessTranslate) {
                            PRACTICE_TYPE.WORD_TRANSLATE -> option.word
                            PRACTICE_TYPE.AUDIO -> if (hasAnswered) option.word else "Option ${index + 1}"
                            else -> option.word_translate
                        }

                        Text(
                            text = displayText,
                            style = MaterialTheme.typography.titleMedium,
                            color = if (hasAnswered && !isCorrect && !isSelected) White50 else White,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    if (hasAnswered) {
                        Spacer(modifier = Modifier.width(8.dp))
                        if (isCorrect) {
                            Icon(
                                imageVector = Icons.Rounded.CheckCircle,
                                contentDescription = "Correct",
                                tint = Success,
                                modifier = Modifier.size(28.dp)
                            )
                        } else if (isSelected) {
                            Icon(
                                imageVector = Icons.Rounded.Cancel,
                                contentDescription = "Wrong",
                                tint = Error,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
