package com.example.notepad.ui.screens

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SignInScreen(
    usernameFlow: StateFlow<String?>,
    onSignIn: (String) -> Unit,
    onSignedInGo: () -> Unit
) {
    val username by usernameFlow.collectAsState()
    LaunchedEffect(username) { if (!username.isNullOrBlank()) onSignedInGo() }

    var input by remember { mutableStateOf("") }

    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
            Text("Welcome", style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("hello there!\nI am Notepad designed by Ronex\n Please Enter a username") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { if (input.isNotBlank()) onSignIn(input) },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Tap here to sign In") }
        }
    }
}