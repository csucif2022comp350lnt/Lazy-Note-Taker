package edu.csuci.lazynotetaker.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["id", "pageNumber"])
data class Page(
    val content: String,
    val id: Int,
    val pageNumber: Int
)


class InvalidPageException(message: String): Exception(message)