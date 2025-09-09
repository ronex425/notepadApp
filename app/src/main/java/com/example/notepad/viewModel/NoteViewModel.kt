package com.example.notepad.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.notepad.NotepadApp
import com.example.notepad.domain.Note

class NoteViewModel(app: NotepadApp) : ViewModel() {
    private val repo = app.noteRepository

    val notes: StateFlow<List<Note>> = repo.observeNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun saveNote(id: Long, title: String, body: String, images: List<String>) =
        viewModelScope.launch {
            repo.upsert(Note(id = id, title = title, body = body, imageUris = images))
        }

    fun deleteNote(note: Note) = viewModelScope.launch { repo.delete(note) }
}