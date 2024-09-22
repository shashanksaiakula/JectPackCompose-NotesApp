package com.example.jectpackcompose_notes.note_Compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jectpackcompose_notes.DB.NoteDatabase
import com.example.jectpackcompose_notes.Dao.NoteDao
import com.example.jectpackcompose_notes.R
import com.example.jectpackcompose_notes.Model.Note
import com.example.jectpackcompose_notes.noteViewModel.NoteViewModel
import com.example.jectpackcompose_notes.repository.NoteRepository

@Composable
fun EditScreen(backToHomeScreen: () -> Unit) {
    val context = LocalContext.current
    val database = NoteDatabase.getDatabase(context)
    val noteDao: NoteDao = database.noteDao()
    val noteRepository = remember { NoteRepository(noteDao) }
    // Assuming you have a way to get dependencies needed for NoteViewModel
    val noteViewModel: NoteViewModel = viewModel(factory = NoteViewModelFactory(noteRepository))

    var titleText by remember { mutableStateOf("") }
    var bodyText by remember { mutableStateOf("") }
    var savePopup by remember { mutableStateOf(false) }
    var deletePopup by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BackIcon(ImageBitmap.imageResource(id = R.drawable.chevron_left)) {
                backToHomeScreen()
            }
            Row(
                modifier = Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                BackIcon(ImageBitmap.imageResource(id = R.drawable.visibility), size = 40.dp, padding = 4.dp) {
                    deletePopup = true
                }
                BackIcon(ImageBitmap.imageResource(id = R.drawable.save), size = 40.dp, padding = 4.dp) {
                    savePopup = true
                }
            }
        }

        // Title and Body Text Fields
        EditTitle(titleText) { titleText = it }
        BodyText(bodyText) { bodyText = it }

        // Save Popup Logic
        if (savePopup) {
            MyAlert(
                isDialogOpen = remember { mutableStateOf(savePopup) },
                title = "SAVE",
                text = "Save changes?",
                confirmButtomText = "Save",
                dismissionButtonText = "Cancel",
                onDesmiss = { savePopup = false },
                confirmButtom = {
                    savePopup = false
                    // Save the note
                    noteViewModel.insert(Note(title = titleText, body = bodyText))
                    backToHomeScreen()
                },
                dismissinButton = {
                    savePopup = false
                    backToHomeScreen()
                }
            )
        }

        // Delete Popup Logic
        if (deletePopup) {
            MyAlert(
                isDialogOpen = remember { mutableStateOf(deletePopup) },
                title = "DELETE",
                text = "Are you sure you want to discard your changes?",
                confirmButtomText = "Keep",
                dismissionButtonText = "Discard",
                onDesmiss = { deletePopup = false
                    backToHomeScreen()
                },
                confirmButtom = {
                    deletePopup = false

                },
                dismissinButton = {
                    backToHomeScreen()
                    deletePopup = false
                }
            )
        }
    }
}

@Composable
fun EditTitle(title: String, onTitleChange: (String) -> Unit) {
    TextField(
        value = title,
        onValueChange = onTitleChange,
        placeholder = { Text(text = "Title", fontSize = 40.sp) },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = TextStyle.Default.copy(
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    )
}

@Composable
fun BodyText(body: String, onBodyChange: (String) -> Unit) {
    TextField(
        value = body,
        onValueChange = onBodyChange,
        placeholder = { Text(text = "Type something...", fontSize = 20.sp) },
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = TextStyle.Default.copy(
            color = Color.White,
            fontSize = 20.sp
        )
    )
}

@Composable
fun BackIcon(imageBitmap: ImageBitmap, size: Dp = 50.dp, padding: Dp = 0.dp, click: () -> Unit) {
    Image(
        bitmap = imageBitmap,
        contentDescription = "Back Icon",
        modifier = Modifier
            .size(size)
            .padding(padding)
            .clickable { click() }
    )
}

// ViewModelFactory for NoteViewModel
class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
