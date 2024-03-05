package com.example.noteapp_primeit_test.presentation.note

sealed interface NoteEvent {
    data class TitleChange(val value: String): NoteEvent
    data class ContentChange(val value: String): NoteEvent
    object Save : NoteEvent
    object NavigateBack : NoteEvent
    object DeleteNote : NoteEvent
}