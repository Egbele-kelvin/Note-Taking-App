package com.example.noteapp_primeit_test.data.mapping

import com.example.noteapp_primeit_test.data.entity.NoteEntity
import com.example.noteapp_primeit_test.domain.model.NoteModel

fun NoteEntity.asExternalModel(): NoteModel = NoteModel(
    id, title, content
)

fun NoteModel.toEntity(): NoteEntity = NoteEntity(
    id, title, content
)
