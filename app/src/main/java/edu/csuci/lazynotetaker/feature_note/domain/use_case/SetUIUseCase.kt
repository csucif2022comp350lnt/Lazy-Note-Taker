package edu.csuci.lazynotetaker.feature_note.domain.use_case

import edu.csuci.lazynotetaker.feature_note.domain.util.AppTheme
import edu.csuci.lazynotetaker.feature_note.domain.util.AppTheme.*
import edu.csuci.lazynotetaker.ui.theme.AmoledColorPalette
import edu.csuci.lazynotetaker.ui.theme.DarkColorPalette
import edu.csuci.lazynotetaker.ui.theme.LightColorPalette
import edu.csuci.lazynotetaker.ui.theme.curTheme

class SetUIUseCase (appTheme: AppTheme) {
    operator fun invoke(
        appTheme: AppTheme = Light()

    ) {
        curTheme = when (appTheme) {
            is Light -> {
                LightColorPalette
            }
            is Dark -> {
                DarkColorPalette
            }
            is Amoled -> {
                AmoledColorPalette
            }

        }

    }
}
