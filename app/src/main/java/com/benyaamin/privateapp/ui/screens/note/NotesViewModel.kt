package com.benyaamin.privateapp.ui.screens.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benyaamin.privateapp.models.Note
import com.benyaamin.privateapp.repositories.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val _notesFlow = MutableStateFlow<List<Note>>(emptyList())
    val notesFlow: StateFlow<List<Note>> = _notesFlow

    init {
        reloadList()
    }

    fun reloadList() = viewModelScope.launch {
        val result = noteRepository.getNotes()
        _notesFlow.emit(result)
    }

    fun newNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
        reloadList()
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
        reloadList()
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
        reloadList()
    }

    fun searchWith(query: String) = viewModelScope.launch {
        val result = noteRepository.search(query)
        _notesFlow.emit(result)
    }

}