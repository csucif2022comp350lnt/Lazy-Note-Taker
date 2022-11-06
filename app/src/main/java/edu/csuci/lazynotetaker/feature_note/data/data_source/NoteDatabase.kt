package edu.csuci.lazynotetaker.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.csuci.lazynotetaker.feature_note.domain.model.Note
import edu.csuci.lazynotetaker.feature_note.domain.model.Page


@Database(
    entities = [Note::class, Page::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
        /*@Volatile
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

        }*/
    }



}