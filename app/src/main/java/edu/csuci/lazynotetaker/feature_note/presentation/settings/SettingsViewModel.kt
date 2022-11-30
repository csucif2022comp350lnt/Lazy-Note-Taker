package edu.csuci.lazynotetaker.feature_note.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.csuci.lazynotetaker.feature_note.domain.model.Note
import edu.csuci.lazynotetaker.feature_note.domain.use_case.NoteUseCases
import edu.csuci.lazynotetaker.feature_note.domain.util.NoteOrder
import edu.csuci.lazynotetaker.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(SettingsState())
    val state: State<SettingsState> = _state

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.ToggleUISection -> {
                    _state.value = state.value.copy(
                        isUISectionVisible = !state.value.isUISectionVisible
                    )
            }
            is SettingsEvent.ChangeUITheme -> {
                    _state.value = state.value.copy(

                    )
            }
        }
    }


}