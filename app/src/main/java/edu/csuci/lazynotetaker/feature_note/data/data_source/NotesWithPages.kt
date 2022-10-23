package edu.csuci.lazynotetaker.feature_note.data.data_source

import androidx.room.Embedded
import androidx.room.Relation
import edu.csuci.LazyNoteTaker.feature_note.domain.model.Note
import edu.csuci.lazynotetaker.feature_note.domain.model.Page

data class NotesWithPages (
    @Embedded val note: Note,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val page: Page
)
