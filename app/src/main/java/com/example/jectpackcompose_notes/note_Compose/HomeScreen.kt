package com.example.jectpackcompose_notes.note_Compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jectpackcompose_notes.R

@Composable
fun HomeScreen(goToEditScreen :() ->Unit, goToSearchScreen :()-> Unit) {
    Column(
        modifier = Modifier.padding(30.dp),
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
                Log.e("add", "HomeScreen: search" )
                goToSearchScreen()
            }
        }
        Column {
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            BackIcon(
                imageBitmap = ImageBitmap.imageResource(id = R.drawable.add),
                size = 40.dp,
            ) {
                goToEditScreen()
            }
        }
    }
}