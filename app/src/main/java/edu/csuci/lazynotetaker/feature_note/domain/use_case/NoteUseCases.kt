package edu.csuci.LazyNoteTaker.feature_note.domain.use_case

import edu.csuci.lazynotetaker.feature_note.domain.use_case.GetPageUseCase
import edu.csuci.lazynotetaker.feature_note.domain.use_case.GetPagesUseCase

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteUseCase: GetNoteUseCase,
    val getPagesUseCase: GetPagesUseCase,
    val getPageUseCase: GetPageUseCase
)
