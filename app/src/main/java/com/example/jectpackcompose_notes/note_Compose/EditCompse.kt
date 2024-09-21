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
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import com.example.jectpackcompose_notes.R // Your package R file

@Composable
fun EditScreen(backToHomeScreen :()-> Unit) {
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
                BackIcon(
                    ImageBitmap.imageResource(id = R.drawable.visibility),
                    size = 40.dp,
                    padding = 4.dp
                ) {
                    deletePopup = true
                }
                BackIcon(
                    ImageBitmap.imageResource(id = R.drawable.save),
                    size = 40.dp,
                    padding = 4.dp
                ) {
                    savePopup = true
                }
            }
        }
        EditTitle()
        BodyText()

        if (savePopup) {
            MyAlert(isDialogOpen = remember { mutableStateOf(savePopup) },
                title = "SAVE",
                text = "Save changes ?",
                confirmButtomText = "Save",
                dismissionButtonText = "Cancel",
                onDesmiss = {
                    savePopup = false
                },
                confirmButtom = {
                    savePopup = false
                    backToHomeScreen()

                },
                dismissinButton = {
                    savePopup = false
                    backToHomeScreen()
                }
            )

        }
        if (deletePopup) {
            MyAlert(isDialogOpen = remember { mutableStateOf(deletePopup) },
                title = "DELETE",
                text = "Are your sure you want discard your changes ?",
                confirmButtomText = "Keep",
                dismissionButtonText = "Discard",
                onDesmiss = {
                    deletePopup = false
                },
                confirmButtom = {
                    deletePopup = false
                    backToHomeScreen()

                },
                dismissinButton = {
                    deletePopup = false
                    backToHomeScreen()
                }
            )

        }

    }
}

@Composable
fun EditTitle() {
    var titleText by remember { mutableStateOf("") }
    TextField(
        value = titleText,
        onValueChange = { titleText = it },
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
fun BodyText() {
    var bodyText by remember { mutableStateOf("") }
    TextField(
        value = bodyText,
        onValueChange = { bodyText = it },
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
fun BackIcon(imageBitmap: ImageBitmap, size: Dp = 50.dp, padding: Dp = 0.dp, click: () -> Unit,) {
    Image(
        bitmap = imageBitmap,
        contentDescription = "Back Icon",
        modifier = Modifier
            .size(size)
            .padding(padding)
            .clickable { click() }
    )
}


