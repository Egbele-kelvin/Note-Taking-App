@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.noteapp_primeit_test.presentation.screen


import com.example.noteapp_primeit_test.domain.model.NoteModel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp



@Composable
fun NoteListScreen(
    noteList: List<NoteModel>,
    onNoteClick: (NoteModel) -> Unit,
    onAddNoteClick: () -> Unit
) {
    Scaffold(
        containerColor =  Color(0xFFFFA867),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNoteClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "add note"
                )
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 15.dp + padding.calculateTopPadding(),
                bottom = 15.dp + padding.calculateBottomPadding()
            )
        ) {
            item {
                Text(
                    text = "Notes",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            items(noteList) { note ->
                Card(
                    modifier = Modifier
                        .size(width = 340.dp, height = 170.dp)
                        .padding(vertical = 14.dp,),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                ){
                    ListItem(
                        headlineText = {
                            Text(
                                text = note.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        supportingText = {
                            Text(
                                text = note.content,
                                maxLines = 6,
                                overflow = TextOverflow.Ellipsis
                            )
                        },

                        modifier = Modifier
                            .clickable(
                            onClick = {
                                onNoteClick(note)
                            }
                        )
                    )
                }

            }
        }
    }
}