package com.example.notepad.ui.screens


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.Image
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    noteId: Long,
    loadNote: () -> com.example.notepad.domain.Note?,
    onSave: (id: Long, title: String, body: String, images: List<String>) -> Unit,
    onCancel: () -> Unit
) {
    val existing = remember { loadNote() }
    var title by remember { mutableStateOf(existing?.title ?: "") }
    var body by remember { mutableStateOf(existing?.body ?: "") }
    var images by remember { mutableStateOf(existing?.imageUris ?: emptyList()) }

    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(), // built-in Photo Picker
        onResult = { uris: List<Uri> ->
            images = images + uris.map { it.toString() }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == 0L) "New Note" else "Edit Note") },
                actions = {
                    TextButton(onClick = { onSave(noteId, title, body, images) }) { Text("Save") }
                },
                navigationIcon = { TextButton(onClick = onCancel) { Text("Back") } }
            )
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = body, onValueChange = { body = it }, label = { Text("Body") }, modifier = Modifier.fillMaxWidth(), minLines = 6)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) { Text("Add Images") }
            }
            if (images.isNotEmpty()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(images) { uri ->
                        ElevatedCard {
                            Column(Modifier.padding(8.dp)) {
                                AsyncImage(model = uri, contentDescription = null, modifier = Modifier.size(120.dp))
                                Spacer(Modifier.height(4.dp))
                                TextButton(onClick = { images = images.filterNot { it == uri } }) { Text("Remove") }
                            }
                        }
                    }
                }
            }
        }
    }
}