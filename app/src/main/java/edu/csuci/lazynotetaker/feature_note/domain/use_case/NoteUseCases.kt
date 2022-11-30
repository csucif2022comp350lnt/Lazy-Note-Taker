package edu.csuci.lazynotetaker.feature_note.domain.use_case

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val deletePageUseCase: DeletePageUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val addPageUseCase: AddPageUseCase,
    val getMaxIdFromPage: getMaxIdFromPage,
    val getNoteUseCase: GetNoteUseCase,
    val getPagesUseCase: GetPagesUseCase,
    val getPageUseCase: GetPageUseCase
)
