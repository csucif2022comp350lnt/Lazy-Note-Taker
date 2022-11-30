package edu.csuci.lazynotetaker.feature_note.presentation.add_edit_note

import androidx.compose.material.TextField
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.csuci.lazynotetaker.feature_note.domain.model.InvalidNoteException
import edu.csuci.lazynotetaker.feature_note.domain.model.Note
import edu.csuci.lazynotetaker.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.csuci.lazynotetaker.feature_note.domain.model.Page
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val _state = mutableStateOf(AddEditNoteState())
    val state: State<AddEditNoteState> = _state
    private var getPagesJob: Job? = null


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
    private var _currentPageNumber = mutableStateOf(
        PageNumberState(
            pageNumber = 0
        )
    )
    private var numberOfPages = 10
    var currentPageNumber: State<PageNumberState> = _currentPageNumber

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteUseCase(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        if (noteId != null) {

                            noteUseCases.getPageUseCase(noteId, currentPageNumber.value.pageNumber)
                                ?.also { page ->
                                    if (page != null) {
                                        _noteContent.value = noteContent.value.copy(
                                            text = page.content,
                                            isHintVisible = false
                                        )
                                    }

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
            is AddEditNoteEvent.ChangePage -> {
                if (event.pageNumber != null  && currentNoteId != null) {
                    currentPageNumber.value.pageNumber = event.pageNumber
                    noteContent.value.copy(
                        text = ""
                    )
                    viewModelScope.launch {
                        noteUseCases.getPageUseCase(
                            currentNoteId!!, event.pageNumber
                        ).also { page ->
                            if (page != null) {
                                _noteContent.value = noteContent.value.copy(
                                    text = page.content,
                                    isHintVisible = false
                                )
                            } else {
                                _noteContent.value = noteContent.value.copy(
                                    text = "",
                                    isHintVisible = true
                                )
                            }

                        }


                    }
                }
//                currentPageNumber.value.pageNumber = event.pageNumber
//                viewModelScope.launch {
//
//                    currentNoteId?.let {
//                        noteUseCases.getPageUseCase(currentNoteId!!, event.pageNumber
//                        ).also { page ->
//                            if (page != null) {
//                                _noteContent.value = noteContent.value.copy(
//                                    text = page.content,
//                                    isHintVisible = false
//                                )
//                            } else {
//                                _noteContent.value = noteContent.value.copy(
//                                    text = "",
//                                    isHintVisible = true
//                                )
//                            }
//
//                        }
//                    }
//
//
//
//                }

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

                        if (currentNoteId != null) {
                            noteUseCases.addPageUseCase(
                                Page(
                                    content = noteContent.value.text,
                                    pageNumber = currentPageNumber.value.pageNumber,
                                    id = currentNoteId!!
                                )
                            )
                        }

                       //_eventFlow.emit(UiEvent.SavedNote)

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

//    private fun getPages(noteOrder: NoteOrder) {
//        getPagesJob?.cancel()
//        getPagesJob = noteUseCases.getPagesUseCase()
//            .onEach { pages ->
//                _state.value = state.value.copy(
//                    pages = pages
//                )
//
//            }
//            .launchIn(viewModelScope)
//    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SavedNote: UiEvent()
    }


}