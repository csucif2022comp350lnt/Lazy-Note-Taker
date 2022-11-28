package edu.csuci.lazynotetaker.feature_note.domain.model

import androidx.compose.runtime.State
import androidx.room.Entity
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.PageNumberState

@Entity(primaryKeys = ["id", "pageNumber"])
data class Page(
    val content: String,
    val id: Int,
    val pageNumber: Int
)


class InvalidPageException(message: String): Exception(message)