package edu.csuci.LazyNoteTaker.feature_note.data.data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.csuci.LazyNoteTaker.feature_note.domain.model.Note
import edu.csuci.lazynotetaker.feature_note.domain.model.Page


@Database(
    entities = [Note::class, Page::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getInstance(context:Context): NoteDatabase {
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "notes_db"
                ).build().also {
                    INSTANCE = it
                }
            }
            //const val DATABASE_NAME = "notes_db"
        }
    }



}