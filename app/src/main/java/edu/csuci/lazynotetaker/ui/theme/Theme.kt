package edu.csuci.lazynotetaker.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import edu.csuci.LazyNoteTaker.ui.theme.DarkGray
import edu.csuci.LazyNoteTaker.ui.theme.LightBlue
import edu.csuci.LazyNoteTaker.ui.theme.Shapes
import edu.csuci.LazyNoteTaker.ui.theme.Typography

private val DarkColorPalette = darkColors(
    primary = Color.White,
    background = DarkGray,
    onBackground = Color.White,
    surface = LightBlue,
    onSurface = DarkGray
)

@Composable
fun LazyNoteTakerTheme(darkTheme: Boolean = true, content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}