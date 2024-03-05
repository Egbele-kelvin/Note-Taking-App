package com.example.noteapp_primeit_test.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp_primeit_test.data.dao.NoteDao
import com.example.noteapp_primeit_test.data.entity.NoteEntity


@Database(
    version = 1,
    entities = [NoteEntity::class],
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {

        abstract val dao: NoteDao

        companion object {
            const val name = "database"
        }

}