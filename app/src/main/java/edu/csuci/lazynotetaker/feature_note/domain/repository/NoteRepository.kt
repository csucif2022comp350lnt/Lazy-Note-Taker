package edu.csuci.LazyNoteTaker.feature_note.domain.repository

import edu.csuci.LazyNoteTaker.feature_note.domain.model.Note
import edu.csuci.lazynotetaker.feature_note.data.data_source.NotesWithPages
import edu.csuci.lazynotetaker.feature_note.domain.model.Page
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun insertPage(page: Page)

    suspend fun deleteNote(note: Note)

    suspend fun getNotesWithPages(id: Int): List<NotesWithPages>
}