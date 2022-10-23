package edu.csuci.lazynotetaker.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Page (
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val title: String,
    val content: String,
    val pageNumber: Int

)