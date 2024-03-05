package com.example.noteapp_primeit_test.presentation.utils

sealed interface UiEvent {
    data class Navigate(val route: String): UiEvent
    object NavigateBack : UiEvent
}