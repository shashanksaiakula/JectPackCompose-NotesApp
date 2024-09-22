package com.example.jectpackcompose_notes.note_Compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jectpackcompose_notes.DB.NoteDatabase
import com.example.jectpackcompose_notes.Dao.NoteDao
import com.example.jectpackcompose_notes.Model.Note
import com.example.jectpackcompose_notes.R
import com.example.jectpackcompose_notes.noteViewModel.NoteViewModel
import com.example.jectpackcompose_notes.repository.NoteRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun HomeScreen(goToEditScreen: (note :Note?) -> Unit, goToSearchScreen: () -> Unit) {

    var items = remember { mutableStateListOf<Note>() }

//    var items = arrayListOf<Note>()
    val context = LocalContext.current
    val database = NoteDatabase.getDatabase(context)
    val noteDao: NoteDao = database.noteDao()
    val noteRepository = remember { NoteRepository(noteDao) }
    // Assuming you have a way to get dependencies needed for NoteViewModel
    val noteViewModel: NoteViewModel = viewModel(factory = NoteViewModelFactory(noteRepository))

//    runBlocking {
//        launch {
//            items= noteViewModel.getAllData() as ArrayList<Note>
//            Log.e("Check",items.toString())
//        }
//    }

    LaunchedEffect(Unit) {
        items.addAll(noteViewModel.getAllData()) // Assuming this returns List<Note>
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
//            .background(Color.Blue)
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "NOTES",
                fontSize = 30.sp
            )

            BackIcon(
                imageBitmap = ImageBitmap.imageResource(id = R.drawable.search),
                size = 40.dp,

                ) {
                Log.e("add", "HomeScreen: search")
                goToSearchScreen()
            }
        }

        if (items.isEmpty()) {

            Column(
//                modifier = Modifier.
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rafiki), contentDescription = "raf",
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Create your first note !",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
//                    .background(Color.Red)
            ) {
                items(items) {
                    it.title?.let { it1 ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .padding(5.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(
                                        16.dp,
                                        16.dp,
                                        16.dp,
                                        16.dp
                                    )
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                it1, fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(5.dp),
                            )
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                BackIcon(
                                    imageBitmap = ImageBitmap.imageResource(id = R.drawable.delete),
                                    size = 28.dp,
                                    padding = 3.dp
                                ) {
                                    noteViewModel.delete(it)
                                    items.remove(it)
                                }
                            }

                        }
                    }
                }

            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp) // Adjust padding as needed

        ) {
            BackIcon(
                imageBitmap = ImageBitmap.imageResource(id = R.drawable.add),
                size = 40.dp,
            ) {
                goToEditScreen(null)
            }
        }
    }
}

