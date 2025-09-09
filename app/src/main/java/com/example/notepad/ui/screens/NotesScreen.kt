package com.example.notepad.ui.screens

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.notepad.domain.Note
import kotlinx.coroutines.flow.StateFlow
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    notesState: StateFlow<List<Note>>,   // ✅ changed from State<List<Note>>
    onAdd: () -> Unit,
    onEdit: (Long) -> Unit,
    onSignOut: () -> Unit,
    onDelete: (Note) -> Unit
) {
    // ✅ Collect the StateFlow into a Compose-friendly State
    val notes by notesState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hello! these are your Current Notes") },
                actions = {
                    TextButton(onClick = onSignOut) { Text("Sign out") }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) { Text("+") }
        }
    ) { padding ->
        if (notes.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No notes yet. Tap + to add one.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(notes) { n ->
                    ElevatedCard(
                        onClick = { onEdit(n.id) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(n.title, style = MaterialTheme.typography.titleLarge)
                            if (n.body.isNotBlank()) {
                                Spacer(Modifier.height(4.dp))
                                Text(n.body.take(140))
                            }
                            Spacer(Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TextButton(onClick = { onEdit(n.id) }) { Text("Edit") }
                                TextButton(onClick = { onDelete(n) }) { Text("Delete") }
                            }
                        }
                    }
                }
            }
        }
    }
}
