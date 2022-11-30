package edu.csuci.lazynotetaker.feature_note.domain.model

import androidx.compose.runtime.State
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note.PageNumberState

@Entity(primaryKeys = ["id", "pageNumber"])
//@Entity
data class Page(
    //@PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val content: String,
    val pageNumber: Int
)


class InvalidPageException(message: String): Exception(message)