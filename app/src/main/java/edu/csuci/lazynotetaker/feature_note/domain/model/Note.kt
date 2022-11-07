package edu.csuci.lazynotetaker.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.csuci.lazynotetaker.ui.theme.*


@Entity

data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
)
{
    companion object {
        val noteColors = listOf(Red, Green, Violet, Blue, Pink)
    }
}

class InvalidNoteException(message: String): Exception(message)