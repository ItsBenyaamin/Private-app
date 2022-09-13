package com.benyaamin.privateapp.repositories

import com.benyaamin.privateapp.models.Note
import com.benyaamin.privateapp.models.Password
import com.benyaamin.privateapp.persistence.NoteDao
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun getNotes(): List<Note> {
        return withContext(IO) {
            noteDao.getNotes()
        }
    }

    suspend fun insertNote(note: Note) {
        withContext(IO) {
            noteDao.insert(note)
        }
    }

    suspend fun updateNote(note: Note) {
        withContext(IO) {
            noteDao.update(note)
        }
    }

    suspend fun deleteNote(note: Note) {
        withContext(IO) {
            noteDao.delete(note)
        }
    }

    suspend fun search(query: String): List<Note> {
        return withContext(IO) {
            noteDao.search("%$query%")
        }
    }

}