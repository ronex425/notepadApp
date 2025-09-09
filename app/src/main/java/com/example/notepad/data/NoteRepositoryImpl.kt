package com.example.notepad.data

import com.example.notepad.data.local.NoteDao
import com.example.notepad.data.local.NoteEntity
import com.example.notepad.domain.Note
import com.example.notepad.domain.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(private val dao: NoteDao): NoteRepository {

    private fun NoteEntity.toDomain() = Note(
        id = id,
        title = title,
        body = body,
        imageUris = imageUrisCsv?.split("|")?.filter { it.isNotBlank() } ?: emptyList(),
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    private fun Note.toEntity() = NoteEntity(
        id = id,
        title = title,
        body = body,
        imageUrisCsv = if (imageUris.isEmpty()) null else imageUris.joinToString("|"),
        createdAt = createdAt,
        updatedAt = System.currentTimeMillis()
    )

    override fun observeNotes(): Flow<List<Note>> =
        dao.observeNotes().map { list -> list.map { it.toDomain() } }

    override suspend fun get(id: Long): Note? = dao.getById(id)?.toDomain()

    override suspend fun upsert(note: Note): Long {
        return if (note.id == 0L) {
            dao.insert(note.toEntity())
        } else {
            dao.update(note.toEntity()); note.id
        }
    }

    override suspend fun delete(note: Note) {
        dao.delete(note.toEntity())
    }
}