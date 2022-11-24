package edu.csuci.lazynotetaker.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import edu.csuci.lazynotetaker.feature_note.domain.model.Note
import edu.csuci.lazynotetaker.feature_note.domain.model.Page
import kotlinx.coroutines.flow.Flow

@Dao

interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM page")
    fun getPages(): Flow<List<Page>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int) :Note?

    @Query("SELECT * FROM page WHERE id = :id")
    suspend fun getPageById(id: Int) :Page?

    @Query("SELECT * FROM page WHERE id = :id AND pageNumber = :pageNumber")
    suspend fun getPageByIdAndPageNumber(id: Int, pageNumber: Int) :Page?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPage(page: Page)

    @Delete
    suspend fun deleteNote(note: Note)

    @Delete
    suspend fun deletePage(page: Page)

    @Transaction
    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNotesWithPages(id: Int): List<NotesWithPages>

}