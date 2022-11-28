package edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true,
    val pages: List<edu.csuci.lazynotetaker.feature_note.domain.model.Page> = emptyList()


)
