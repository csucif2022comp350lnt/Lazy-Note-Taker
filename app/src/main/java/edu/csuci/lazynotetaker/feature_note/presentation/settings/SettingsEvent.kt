package edu.csuci.lazynotetaker.feature_note.presentation.settings

sealed class SettingsEvent {
    object ToggleUISection: SettingsEvent()
    object ChangeUITheme : SettingsEvent()
}
