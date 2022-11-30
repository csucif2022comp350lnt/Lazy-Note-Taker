package edu.csuci.lazynotetaker.feature_note.presentation.settings

import edu.csuci.lazynotetaker.feature_note.domain.util.AppTheme
import edu.csuci.lazynotetaker.ui.theme.AmoledColorPalette

data class SettingsState(
    val appTheme: AppTheme = AppTheme.Light(),

    val isUISectionVisible: Boolean = false
)
