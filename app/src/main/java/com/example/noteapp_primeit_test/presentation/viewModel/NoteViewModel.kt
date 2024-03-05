package com.example.noteapp_primeit_test.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp_primeit_test.domain.model.NoteModel
import com.example.noteapp_primeit_test.domain.repository.NoteRepository
import com.example.noteapp_primeit_test.domain.security.AESecurityManager
import com.example.noteapp_primeit_test.presentation.note.NoteEvent
import com.example.noteapp_primeit_test.presentation.note.NoteState
import com.example.noteapp_primeit_test.presentation.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class  NoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val savedStateHandle: SavedStateHandle,
    private  val securityManager: AESecurityManager
): ViewModel() {
    private val _state = MutableStateFlow(NoteState())
    val state = _state.asStateFlow()

    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()

    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _event.send(event)
        }
    }

    init {
        savedStateHandle.get<String>("id")?.let {
            val id = it.toInt()
            viewModelScope.launch {
                try {
                    repository.getNoteById(id)?.let { note ->
                        // Decrypt the title and content after retrieval
                        val decryptedTitle = securityManager.decrypt(note.title)
                        val decryptedContent = securityManager.decrypt(note.content)
                        _state.update { screenState ->
                            screenState.copy(
                                id = note.id,
                                title = decryptedTitle,
                                content = decryptedContent
                            )
                        }
                    }
                } catch (_: Exception) {

                }

            }
        }
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.ContentChange -> {
                _state.update {
                    it.copy(
                        content = event.value
                    )
                }
            }

            is NoteEvent.TitleChange -> {
                _state.update {
                    it.copy(
                        title = event.value
                    )
                }
            }

            NoteEvent.NavigateBack -> sendEvent(UiEvent.NavigateBack)
            NoteEvent.Save -> {
                viewModelScope.launch {
                    val state = state.value
                    // Encrypt title and content before saving
                    val encryptedTitle = securityManager.encrypt(state.title)
                    val encryptedContent = securityManager.encrypt(state.content)
                    val note = NoteModel(
                        id = state.id,
                        title = encryptedTitle,
                        content = encryptedContent
                    )
                    if (state.id == null) {
                        repository.insertNote(note)
                    } else {
                        repository.updateNote(note)
                    }
                    sendEvent(UiEvent.NavigateBack)
                }
            }

            NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    val state = state.value
                    val note = NoteModel(
                        id = state.id,
                        title = state.title,
                        content = state.content
                    )
                    repository.deleteNote(note)
                }
                sendEvent(UiEvent.NavigateBack)
            }
        }
    }

}