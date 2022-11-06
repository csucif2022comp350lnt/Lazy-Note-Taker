package edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note

import android.net.Uri
import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class InternalStoragePhoto(
        val name: String,
        val uri: Uri?
    )

    data class EnteredTitle(val value: String): AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditNoteEvent()
    data class EnteredContent(val value: String): AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditNoteEvent()
    data class ChangeColor(val color: Int): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}
