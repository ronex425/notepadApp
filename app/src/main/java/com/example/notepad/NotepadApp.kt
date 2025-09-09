package com.example.notepad
import android.app.Application
import com.example.notepad.data.NoteRepositoryImpl
import com.example.notepad.data.local.AppDatabase
import com.example.notepad.data.prefs.AuthPrefs
import com.example.notepad.domain.NoteRepository

class NotepadApp : Application() {
    lateinit var noteRepository: NoteRepository
        private set
    lateinit var authPrefs: AuthPrefs
        private set

    override fun onCreate() {
        super.onCreate()
        val db = AppDatabase.get(this)
        noteRepository = NoteRepositoryImpl(db.noteDao())
        authPrefs = AuthPrefs(this)
    }
}