package edu.csuci.lazynotetaker.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.csuci.lazynotetaker.ui.theme.*


@Entity

data class Note(
    val title: String,
    val timestamp: Long,
    val color: Int,
    val numOfPages: Int,
    @PrimaryKey val id: Int = 1
)
{
    companion object {
        val noteColors = listOf(Red, Green, Violet, Blue, Pink)
    }
}

class InvalidNoteException(message: String): Exception(message)