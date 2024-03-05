package com.example.noteapp_primeit_test.domain.repository

import com.example.noteapp_primeit_test.domain.model.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    
    fun getAllNotes(): Flow<List<NoteModel>>

    suspend fun getNoteById(id: Int): NoteModel?

    suspend fun insertNote(note: NoteModel)

    suspend fun deleteNote(note: NoteModel)

    suspend fun updateNote(note: NoteModel)
}