package edu.csuci.lazynotetaker.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Page(
    val content: String,
    val pageNumber: Int,
    @PrimaryKey val id: Int? = null
)
class InvalidPageException(message: String): Exception(message)