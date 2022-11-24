package edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.csuci.lazynotetaker.feature_note.domain.model.InvalidNoteException
import edu.csuci.lazynotetaker.feature_note.domain.model.Note
import edu.csuci.lazynotetaker.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.csuci.lazynotetaker.feature_note.domain.model.Page
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val _state = mutableStateOf(AddEditNoteState())
    val state: State<AddEditNoteState> = _state


    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
        hint = "Enter title..."
    )
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextFieldState(
        hint = "Enter some content..."
    )
    )
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf<Int> (Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null
    private var currentPageNumber = 1
    private var numberOfPages = 1

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteUseCase(noteId)?.also{ note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible =  false
                        )
                        noteUseCases.getPageUseCase(noteId, currentPageNumber).also { page ->
                            if (page != null) {
                                _noteContent.value = noteContent.value.copy(
                                    text = page.content,
                                    isHintVisible =  false
                                )
                            }
                        }

                        _noteColor.value = note.color

                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNoteUseCase(
                            Note(
                                title = noteTitle.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId,
                                numOfPages = numberOfPages
                            )
                        )
                        noteUseCases.addPageUseCase(
                            Page(
                                content = noteContent.value.text,
                                pageNumber = currentPageNumber,
                                id = currentNoteId!!
                            )
                        )

                        _eventFlow.emit(UiEvent.SavedNote)

                    } catch(e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Error saving note"
                            )
                        )

                    }
                }
            }
            is AddEditNoteEvent.ToggleColorSection -> {
                _state.value = state.value.copy(
                    isColorSectionVisible = true
                )
            }
            else -> {}
        }
    }


    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SavedNote: UiEvent()
    }
//    private fun getPages() {
//        getPagesJob = noteUseCases.getPagesUseCase().onEach { notes ->
//                _state.value = state.value.copy(
//                    page = page
//                )
//
//            }
//            .launchIn(viewModelScope)
//    }

}