package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.iago.reminder.helpers.ButtonSpeaker
import com.iago.reminder.models.Word
import com.iago.reminder.ui.theme.BackgroundDark
import com.iago.reminder.ui.theme.Primary
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.White10
import com.iago.reminder.ui.theme.White70

@Composable
fun DialogWord(
    word: Word,
    openDialog: MutableState<Boolean>
) {
    val context = LocalContext.current

    Dialog(onDismissRequest = { openDialog.value = false }) {
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundDark),
            border = BorderStroke(1.dp, White10),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top row with status & close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (word.active) Primary.copy(alpha = 0.3f) else White.copy(alpha = 0.1f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Schedule,
                                contentDescription = null,
                                tint = White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                text = word.time.take(5),
                                style = MaterialTheme.typography.labelSmall,
                                color = White
                            )
                        }
                    }

                    IconButton(
                        onClick = { openDialog.value = false },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Close",
                            tint = White70
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Word Text
                Text(
                    text = word.word,
                    style = MaterialTheme.typography.displayMedium,
                    color = White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Translation Text
                Text(
                    text = word.word_translate,
                    style = MaterialTheme.typography.titleLarge,
                    color = Primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Audio Speaker Button
                ButtonSpeaker(
                    context = context,
                    word = word.word,
                    size = 56.dp,
                    backgroundColor = Primary,
                    iconColor = White
                )
            }
        }
    }
}
