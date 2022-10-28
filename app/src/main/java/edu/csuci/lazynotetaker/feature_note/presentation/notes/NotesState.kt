package edu.csuci.LazyNoteTaker.feature_note.presentation.notes

import edu.csuci.LazyNoteTaker.feature_note.domain.util.NoteOrder
import edu.csuci.LazyNoteTaker.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<edu.csuci.LazyNoteTaker.feature_note.domain.model.Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
