package com.example.noteapp_primeit_test.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey val id: Int?,
    val title: String,
    val content: String
)