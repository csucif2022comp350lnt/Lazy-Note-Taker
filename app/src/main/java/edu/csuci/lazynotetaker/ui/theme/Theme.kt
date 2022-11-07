package edu.csuci.lazynotetaker.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import edu.csuci.lazynotetaker.ui.theme.DarkGray
import edu.csuci.lazynotetaker.ui.theme.LightBlue
import edu.csuci.lazynotetaker.ui.theme.Shapes
import edu.csuci.lazynotetaker.ui.theme.Typography

private val DarkColorPalette = darkColors(
    primary = White,
    background = DarkGray,
    onBackground = LightGray,
    surface = LightBlue,
    onSurface = DarkGray
)
private val AmoledColorPalette = darkColors(
    primary = White,
    background = Black,
    onBackground = DarkGray,
    surface = LightBlue,
    onSurface = Black
)
private val LightColorPalette = lightColors(
    primary = DarkGray,
    background = White,
    onBackground = LightGray,
    surface = LightBlue,
    onSurface = White
)

@Composable
fun lazynotetakerTheme(darkTheme: Boolean = true, content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}