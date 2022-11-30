package edu.csuci.lazynotetaker.di

import android.app.Application
import androidx.room.Room
import edu.csuci.lazynotetaker.feature_note.data.data_source.NoteDatabase
import edu.csuci.lazynotetaker.feature_note.data.repository.NoteRepositoryImpl
import edu.csuci.lazynotetaker.feature_note.domain.repository.NoteRepository
import edu.csuci.lazynotetaker.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.csuci.lazynotetaker.feature_note.domain.use_case.GetPageUseCase
import edu.csuci.lazynotetaker.feature_note.domain.use_case.GetPagesUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            deletePageUseCase = DeletePageUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            addPageUseCase = AddPageUseCase(repository),
            getNoteUseCase = GetNoteUseCase(repository),
            getMaxIdFromPage = getMaxIdFromPage(repository),
            getPagesUseCase = GetPagesUseCase(repository),
            getPageUseCase = GetPageUseCase(repository)
        )
    }
}