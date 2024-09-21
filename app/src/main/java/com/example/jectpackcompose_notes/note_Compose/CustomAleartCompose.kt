package com.example.jectpackcompose_notes.note_Compose

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy

@Composable
fun MyAlert(isDialogOpen: MutableState<Boolean>, title :String, text :String,
            confirmButtomText : String, dismissionButtonText : String, onDesmiss : ()->Unit, confirmButtom: ()-> Unit,
            dismissinButton :()-> Unit) {
    AlertDialog(
        title = { Text(text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth())  },
        text = { Text(text = text,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth()
        ) },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            securePolicy = SecureFlagPolicy.Inherit
        ),
        onDismissRequest = {
            dismissinButton()
        },
        confirmButton = {
            Button(onClick = {
                Log.e("check", "EditScreen: save button")
                confirmButtom()
            },
                modifier = Modifier.padding(8.dp),
                colors = ButtonDefaults.buttonColors(Color.Green)

            ) {
                Text(text = confirmButtomText)
            }
        },
        dismissButton = {
            Button(onClick = {
                isDialogOpen.value = false
                onDesmiss()
            },
                modifier = Modifier.padding(8.dp),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(text = dismissionButtonText)
            }
        },
        backgroundColor = Color.DarkGray,
        modifier = Modifier
            .fillMaxWidth()
            .border(5.dp, color = Color.Transparent, shape = CircleShape),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)

    )
}