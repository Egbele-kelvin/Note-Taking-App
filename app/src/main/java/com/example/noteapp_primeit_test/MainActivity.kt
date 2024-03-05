package com.example.noteapp_primeit_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapp_primeit_test.presentation.screen.NoteListScreen
import com.example.noteapp_primeit_test.presentation.screen.NoteScreen
import com.example.noteapp_primeit_test.presentation.utils.Route
import com.example.noteapp_primeit_test.presentation.utils.UiEvent
import com.example.noteapp_primeit_test.presentation.viewModel.NoteListViewModel
import com.example.noteapp_primeit_test.presentation.viewModel.NoteViewModel
import com.example.noteapp_primeit_test.ui.theme.NoteAppPrimeITTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppPrimeITTestTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.noteList
                ) {
                    composable(route = Route.noteList) {
                        val viewModel = hiltViewModel<NoteListViewModel>()
                        val noteList by viewModel.noteList.collectAsStateWithLifecycle(initialValue = emptyList())

                        NoteListScreen(
                            noteList = noteList,
                            onNoteClick = {
                                navController.navigate(
                                    Route.note.replace(
                                        "{id}",
                                        it.id.toString()
                                    )
                                )
                            }
                        ) {
                            navController.navigate(Route.note)
                        }
                    }

                    composable(route = Route.note) {
                        val viewModel = hiltViewModel<NoteViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()

                        LaunchedEffect(key1 = true) {
                            viewModel.event.collect { event ->
                                when (event) {
                                    is UiEvent.NavigateBack -> {
                                        navController.popBackStack()
                                    }

                                    else -> Unit
                                }
                            }
                        }

                        NoteScreen(
                            state = state,
                            onEvent = viewModel::onEvent
                        )
                    }
                    }

                }
            }
        }
    }

