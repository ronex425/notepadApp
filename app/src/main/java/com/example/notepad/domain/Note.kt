package com.example.notepad.domain

data class Note(
    val id: Long = 0L,
    val title: String,
    val body: String,
    val imageUris: List<String>,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)