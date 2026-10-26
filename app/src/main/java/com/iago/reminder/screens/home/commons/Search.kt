package com.iago.reminder.screens.home.commons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.iago.reminder.R
import com.iago.reminder.ui.theme.BackgroundDark
import com.iago.reminder.ui.theme.Primary
import com.iago.reminder.ui.theme.White
import com.iago.reminder.ui.theme.White10
import com.iago.reminder.ui.theme.White50
import com.iago.reminder.ui.theme.White70

@Composable
fun SearchInput(
    search: MutableState<String>,
    filter: (text: String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = search.value,
        onValueChange = {
            search.value = it
            filter(it)
        },
        singleLine = true,
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                style = MaterialTheme.typography.bodyMedium,
                color = White50
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = White70
            )
        },
        trailingIcon = {
            if (search.value.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Clear search",
                    tint = White70,
                    modifier = Modifier.clickable {
                        search.value = ""
                        filter("")
                    }
                )
            }
        },
        shape = RoundedCornerShape(28.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = White,
            unfocusedTextColor = White,
            focusedContainerColor = BackgroundDark,
            unfocusedContainerColor = BackgroundDark,
            focusedBorderColor = Primary,
            unfocusedBorderColor = White10,
            cursorColor = Primary
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    )
}
