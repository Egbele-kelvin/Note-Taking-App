package com.example.noteapp_primeit_test.data.repository

import com.example.noteapp_primeit_test.data.dao.NoteDao
import com.example.noteapp_primeit_test.data.mapping.asExternalModel
import com.example.noteapp_primeit_test.data.mapping.toEntity
import com.example.noteapp_primeit_test.domain.model.NoteModel
import com.example.noteapp_primeit_test.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl (
    private val dao: NoteDao
) : NoteRepository {
    override fun getAllNotes(): Flow<List<NoteModel>> {
       return  dao.getAllNotes()
           .map { notes ->
               notes.map {
                   it.asExternalModel()
               }

           }
    }

    override suspend fun getNoteById(id: Int): NoteModel? {
       return dao.getNoteById(id)?.asExternalModel()
    }

    override suspend fun insertNote(note: NoteModel) {
        return dao.insertNote(note.toEntity())
    }

    override suspend fun deleteNote(note: NoteModel) {
       return dao.deleteNote(note.toEntity())
    }

    override suspend fun updateNote(note: NoteModel) {
        return dao.updateNote(note.toEntity())
    }
}