package com.example.notepad.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notepad.NotepadApp

class AppViewModelFactory(private val app: NotepadApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(app) as T
            modelClass.isAssignableFrom(NoteViewModel::class.java) -> NoteViewModel(app) as T
            else -> throw IllegalArgumentException("Unknown VM")
        }
    }
}