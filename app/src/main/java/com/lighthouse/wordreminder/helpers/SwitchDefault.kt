package com.lighthouse.wordreminder.helpers

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lighthouse.wordreminder.ui.theme.BackgroundDark
import com.lighthouse.wordreminder.ui.theme.Primary
import com.lighthouse.wordreminder.ui.theme.White
import com.lighthouse.wordreminder.ui.theme.White10
import com.lighthouse.wordreminder.ui.theme.White50

@Composable
fun SwitchDefault(
    initialValue: Boolean = false,
    modifier: Modifier = Modifier,
    onChange: (value: Boolean) -> Unit
) {
    Switch(
        checked = initialValue,
        onCheckedChange = onChange,
        modifier = modifier,
        colors = SwitchDefaults.colors(
            checkedThumbColor = White,
            checkedTrackColor = Primary,
            checkedBorderColor = Primary,
            uncheckedThumbColor = White50,
            uncheckedTrackColor = BackgroundDark,
            uncheckedBorderColor = White10
        )
    )
}
