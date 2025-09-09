package com.example.notepad.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.notepad.NotepadApp

class AuthViewModel(app: NotepadApp) : ViewModel() {
    private val prefs = app.authPrefs
    val username = prefs.usernameFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    fun signIn(name: String) = viewModelScope.launch {
        prefs.signIn(name.trim())
    }

    fun signOut() = viewModelScope.launch {
        prefs.signOut()
    }
}