package com.example.jectpackcompose_notes

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jectpackcompose_notes.DB.NoteDatabase
import com.example.jectpackcompose_notes.Dao.NoteDao
import com.example.jectpackcompose_notes.noteViewModel.NoteViewModel
import com.example.jectpackcompose_notes.note_Compose.EditScreen
import com.example.jectpackcompose_notes.note_Compose.EditTitle
import com.example.jectpackcompose_notes.note_Compose.HomeScreen
import com.example.jectpackcompose_notes.note_Compose.NoteViewModelFactory
import com.example.jectpackcompose_notes.note_Compose.SearchExample
import com.example.jectpackcompose_notes.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var isNavigateEdit by remember {
                mutableStateOf(false)
            }
            var isNavigateSearch by remember {
                mutableStateOf(false)
            }
            val context = LocalContext.current
            val database = NoteDatabase.getDatabase(context)
            val noteDao: NoteDao = database.noteDao()
            val noteRepository = remember { NoteRepository(noteDao) }
            // Assuming you have a way to get dependencies needed for NoteViewModel
            val noteViewModel: NoteViewModel = viewModel(factory = NoteViewModelFactory(noteRepository))
//            val noteViewModel : NoteViewModel = viewModel()

            runBlocking {
                launch {
                    val data= noteViewModel.getAllData()
                    Log.e("Check",data.toString())
                }
            }

            MyApp {
                if (isNavigateEdit) {
                    EditScreen {
                        isNavigateEdit = false
                    }
                } else if (isNavigateSearch) {
                    SearchExample {
                        isNavigateSearch = false
                    }
                } else {
                    HomeScreen({
                        isNavigateEdit = true
                    },
                        {
                            isNavigateSearch = true
                        }
                    )
                }


//                HomeScreen({
//                    isNavigateEdit = true
//                },
//                    {
//                        isNavigateSearch = true
//                    }
//                )
//                EditScreen {
//                    isNavigateEdit = false
//                }
//                SearchExample {
//                    isNavigateSearch = false
//                }

            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = darkColors(
            background = Color.Black, // Set the background color to black in the theme
            surface = Color.Black // Set surface color to black to handle nested surfaces
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background // Use the background color from the theme
        ) {
            content()
        }
    }
}
