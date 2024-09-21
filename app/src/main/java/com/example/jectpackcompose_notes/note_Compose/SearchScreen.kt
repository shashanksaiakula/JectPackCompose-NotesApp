package com.example.jectpackcompose_notes.note_Compose

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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jectpackcompose_notes.R

@Composable
fun SearchExample(goBackToHome : ()-> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    // Sample data
    val items = listOf("Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape")

    // Filter items based on the search query
    val filteredItems = items.filter {
        it.contains(searchQuery, ignoreCase = true)
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
                )
                ,
            horizontalArrangement =  Arrangement.SpaceBetween,
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
                placeholder = { Text(text = "Search by the keyword...")}
            )
            Image(painter = painterResource(id = R.drawable.close), contentDescription ="close",
            modifier = Modifier
                .clickable { goBackToHome() }
                .size(30.dp)
                .padding(0.dp, 0.dp, 5.dp, 0.dp)
                .background(color = Color.Black, shape = RectangleShape))

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Displaying filtered items
        LazyColumn {
            items(filteredItems) { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}
