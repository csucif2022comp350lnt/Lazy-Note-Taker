package edu.csuci.lazynotetaker.feature_note.domain.util

import androidx.compose.material.Colors
import edu.csuci.lazynotetaker.ui.theme.*

sealed class AppTheme(val appTheme: Colors) {
    class Dark() : AppTheme(appTheme = DarkColorPalette)
    class Light() : AppTheme(appTheme = LightColorPalette)
    class Amoled() : AppTheme(appTheme = AmoledColorPalette)

    fun copy() : AppTheme {
        return when (this) {
            is Dark -> Dark()
            is Light -> Light()
            is Amoled -> Amoled()
        }
    }
}
