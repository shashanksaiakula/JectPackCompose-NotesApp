package com.example.jectpackcompose_notes.note_Compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
fun SearchExample(goBackToHome: () -> Unit, gotEditScreen :(note : Note) ->Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var data = listOf<Note>()

    val context = LocalContext.current
    val database = NoteDatabase.getDatabase(context)
    val noteDao: NoteDao = database.noteDao()
    val noteRepository = remember { NoteRepository(noteDao) }
    // Assuming you have a way to get dependencies needed for NoteViewModel
    val noteViewModel: NoteViewModel = viewModel(factory = NoteViewModelFactory(noteRepository))
//            val noteViewModel : NoteViewModel = viewModel()

    runBlocking {
        launch {
            data = noteViewModel.getAllData()
            Log.e("Check search", data.toString())
        }
    }

    // Sample data
    val items = data

    // Filter items based on the search query
    val filteredItems = items.filter {
        it.title?.contains(searchQuery, ignoreCase = true) == true
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp, color = Color.DarkGray,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Search TextField
            TextField(
                value = searchQuery,
                onValueChange = { query -> searchQuery = query },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent, // Background color
                    focusedIndicatorColor = Color.Transparent, // Remove the bottom line when focused
                    unfocusedIndicatorColor = Color.Transparent // Remove the bottom line when not focused
                ),
                placeholder = { Text(text = "Search by the keyword...") }
            )
            Image(painter = painterResource(id = R.drawable.close), contentDescription = "close",
                modifier = Modifier
                    .clickable { goBackToHome() }
                    .size(30.dp)
                    .padding(0.dp, 0.dp, 5.dp, 0.dp)
                    .background(color = Color.Black, shape = RectangleShape))

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Displaying filtered items
//        LazyColumn {
//            items(filteredItems) { item ->
//                item.title?.let {
//                    Text(
//                        text = it,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp)
//                    )
//                }
//
//            }
//        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(filteredItems) {
                it
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(5.dp)
                        .clickable {
                            Log.e("check", "SearchExample: $it")
                            gotEditScreen(it)
                        }
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
                    it.title?.let { it2 ->
                        Text(
                            it2, fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(5.dp),
                        )
                    }

                }
            }
        }

    }
}
