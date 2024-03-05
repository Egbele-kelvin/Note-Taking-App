package com.example.noteapp_primeit_test.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp_primeit_test.domain.model.NoteModel
import com.example.noteapp_primeit_test.domain.repository.NoteRepository
import com.example.noteapp_primeit_test.domain.security.AESecurityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository,
    private  val securityManager: AESecurityManager
) : ViewModel() {

    val noteList: Flow<List<NoteModel>> = repository.getAllNotes()
        .map { notes ->
            // Decrypt each note's title and content
            notes.map { note ->
                note.copy(
                    title = securityManager.decrypt(note.title),
                    content = securityManager.decrypt(note.content)
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


}