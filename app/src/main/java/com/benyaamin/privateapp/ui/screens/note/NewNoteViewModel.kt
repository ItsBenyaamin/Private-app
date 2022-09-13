package com.benyaamin.privateapp.ui.screens.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benyaamin.privateapp.models.Note
import com.benyaamin.privateapp.repositories.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
) : ViewModel() {

    fun newNote(note: Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }

}