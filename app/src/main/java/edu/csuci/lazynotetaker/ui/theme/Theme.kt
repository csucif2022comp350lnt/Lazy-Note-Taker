package edu.csuci.lazynotetaker.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import edu.csuci.lazynotetaker.feature_note.domain.util.AppTheme
import edu.csuci.lazynotetaker.feature_note.presentation.MainActivity.Companion.appTheme


val DarkColorPalette = darkColors(
        primary = White,
        background = DarkGray,
        onBackground = LightGray,
        surface = LightBlue,
        onSurface = DarkGray
    )
    val AmoledColorPalette = darkColors(
        primary = White,
        background = Black,
        onBackground = DarkGray,
        surface = LightBlue,
        onSurface = Black
    )
    val LightColorPalette = lightColors(
        primary = DarkGray,
        background = White,
        onBackground = LightGray,
        surface = LightBlue,
        onSurface = White
    )

    var curTheme = AmoledColorPalette


