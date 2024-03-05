package com.example.noteapp_primeit_test.data.dependency_injection

import android.content.Context
import androidx.room.Room
import com.example.noteapp_primeit_test.data.NoteDatabase
import com.example.noteapp_primeit_test.data.repository.NoteRepositoryImpl
import com.example.noteapp_primeit_test.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            NoteDatabase.name
        ).build()


    @Provides
    @Singleton
    fun provideNoteRepository(database: NoteDatabase): NoteRepository =
        NoteRepositoryImpl(dao = database.dao)

}