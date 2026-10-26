package com.iago.reminder.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val AppColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = White,
    primaryContainer = Primary,
    onPrimaryContainer = White,
    secondary = Primary,
    onSecondary = White,
    background = Background,
    onBackground = White,
    surface = BackgroundDark,
    onSurface = White,
    surfaceVariant = BackgroundDark,
    onSurfaceVariant = White70,
    surfaceContainer = BackgroundDark,
    surfaceContainerHigh = BackgroundDark,
    outline = White20,
    outlineVariant = Primary,
    error = Error,
    onError = White
)

@Composable
fun WordReminderTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = AppColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                val insetsController = WindowCompat.getInsetsController(window, view)
                insetsController.isAppearanceLightStatusBars = false
                insetsController.isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
