package com.example.notepad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.notepad.ui.nav.Routes
import com.example.notepad.ui.screens.EditNoteScreen
import com.example.notepad.ui.screens.NotesScreen
import com.example.notepad.ui.screens.SignInScreen
import com.example.notepad.viewModel.AppViewModelFactory
import com.example.notepad.viewModel.AuthViewModel
import com.example.notepad.viewModel.NoteViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as NotepadApp
        setContent {
            val factory = AppViewModelFactory(app)
            val authVm: AuthViewModel = viewModel(factory = factory)
            val noteVm: NoteViewModel = viewModel(factory = factory)

            val nav = rememberNavController()

            NavHost(
                navController = nav,
                startDestination = Routes.SignIn
            ) {
                composable(Routes.SignIn) {
                    SignInScreen(
                        usernameFlow = authVm.username,
                        onSignIn = { name -> authVm.signIn(name) },
                        onSignedInGo = { nav.navigate(Routes.Notes) { popUpTo(Routes.SignIn) { inclusive = true } } }
                    )
                }
                composable(Routes.Notes) {
                    NotesScreen(
                        notesState = noteVm.notes,
                        onAdd = { nav.navigate(Routes.EditNote) },
                        onEdit = { id -> nav.navigate("${Routes.EditNote}?id=$id") },
                        onSignOut = {
                            authVm.signOut()
                            nav.navigate(Routes.SignIn) { popUpTo(Routes.Notes) { inclusive = true } }
                        },
                        onDelete = { noteVm.deleteNote(it) }
                    )
                }
                composable(
                    route = "${Routes.EditNote}?id={id}",
                    arguments = listOf(navArgument("id") { defaultValue = 0L; type = NavType.LongType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getLong("id") ?: 0L
                    EditNoteScreen(
                        noteId = id,
                        loadNote = { noteVm.notes.value.find { it.id == id } },
                        onSave = { id0, title, body, images ->
                            noteVm.saveNote(id0, title, body, images)
                            nav.popBackStack()
                        },
                        onCancel = { nav.popBackStack() }
                    )
                }
            }
        }
    }
}