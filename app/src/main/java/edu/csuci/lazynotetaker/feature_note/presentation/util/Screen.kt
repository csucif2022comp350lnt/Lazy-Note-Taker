package edu.csuci.lazynotetaker.feature_note.presentation.util

sealed class Screen(val route: String) {
    object NotesStreen: Screen("notes_screen")
    object AddEditNoteScreen: Screen("add_edit_note_screen")
}
