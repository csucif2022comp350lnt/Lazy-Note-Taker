package edu.csuci.lazynotetaker.feature_note.data.repository

import edu.csuci.lazynotetaker.feature_note.data.data_source.NoteDao
import edu.csuci.lazynotetaker.feature_note.domain.model.Note
import edu.csuci.lazynotetaker.feature_note.domain.repository.NoteRepository
import edu.csuci.lazynotetaker.feature_note.data.data_source.NotesWithPages
import edu.csuci.lazynotetaker.feature_note.domain.model.Page
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
): NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override fun getPages(): Flow<List<Page>> {
        return dao.getPages()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun getPageById(id: Int): Page? {
        return dao.getPageById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override suspend fun deletePage(page: Page){
        dao.deletePage(page)
    }

    override suspend fun insertPage(page: Page) {
        dao.insertPage(page)
    }

    override suspend fun getNotesWithPages(id: Int): List<NotesWithPages> {
        return dao.getNotesWithPages(id)
    }
}