package edu.csuci.lazynotetaker.feature_note.presentation.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.csuci.lazynotetaker.feature_note.domain.util.AppTheme
import edu.csuci.lazynotetaker.feature_note.domain.util.AppTheme.*
import edu.csuci.lazynotetaker.feature_note.domain.util.AppTheme.Amoled
import edu.csuci.lazynotetaker.feature_note.presentation.MainActivity
import edu.csuci.lazynotetaker.feature_note.presentation.notes.components.DefaultRadioButton
import edu.csuci.lazynotetaker.ui.theme.*
import edu.csuci.lazynotetaker.feature_note.presentation.MainActivity.Companion.appTheme


@Composable
fun UISection(
    modifier: Modifier = Modifier,
    appTheme: AppTheme = Dark(),
    onThemeChange: (AppTheme) -> Unit

) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.onBackground)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DefaultRadioButton(
                text = "Light",
                selected = MainActivity.appTheme == LightColorPalette,
                onSelect = { MainActivity.appTheme = LightColorPalette }
            )
            Spacer(modifier = Modifier.width(0.dp))
            DefaultRadioButton(
                text = "Dark",
                selected = MainActivity.appTheme == DarkColorPalette,
                onSelect = { MainActivity.appTheme = DarkColorPalette }
            )
            Spacer(modifier = Modifier.width(0.dp))
            DefaultRadioButton(
                text = "AMOLED",
                selected = MainActivity.appTheme == AmoledColorPalette,
                onSelect = { MainActivity.appTheme = AmoledColorPalette }
            )
        }
    }

}