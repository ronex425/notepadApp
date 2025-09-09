package com.example.notepad.domain


import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun observeNotes(): Flow<List<Note>>
    suspend fun get(id: Long): Note?
    suspend fun upsert(note: Note): Long
    suspend fun delete(note: Note)
}