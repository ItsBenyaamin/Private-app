package com.benyaamin.privateapp.di

import com.benyaamin.privateapp.persistence.NoteDao
import com.benyaamin.privateapp.persistence.PasswordDao
import com.benyaamin.privateapp.persistence.TodoDao
import com.benyaamin.privateapp.repositories.NoteRepository
import com.benyaamin.privateapp.repositories.PasswordRepository
import com.benyaamin.privateapp.repositories.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePasswordRepository(passwordDao: PasswordDao): PasswordRepository {
        return PasswordRepository(passwordDao)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepository(noteDao)
    }

    @Provides
    @Singleton
    fun provideTodoRepository(todoDao: TodoDao): TodoRepository {
        return TodoRepository(todoDao)
    }

}