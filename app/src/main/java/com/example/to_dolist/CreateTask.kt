package com.example.to_dolist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(navController: NavHostController) {

    val titleState = remember { mutableStateOf("") }
    val descriptionState = remember { mutableStateOf("") }
    val dateState = remember { mutableStateOf("") }
    val fields = listOf(
        "Title" to titleState,
        "Description" to descriptionState,
        "Date" to dateState
    )

    val number = 20
    val customAppBarColors = Color(red = number, green = number, blue = number)

    Column {
        TopAppBar(
            title = { Text(text = "Create task") },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = customAppBarColors),
            navigationIcon = {
                IconButton(onClick = { navController.navigate("home") }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                Button(modifier = Modifier.padding(end = 14.dp),
                    onClick = {

                    },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    border = BorderStroke(1.dp, Color.White)
                ) {
                    Text(text = "Save", color = Color.White)
                }
            }
        )
        Column(modifier = Modifier.padding(16.dp)) {
            fields.forEach { (fieldName, fieldValueState) ->
                OutlinedTextField(
                    value = fieldValueState.value,
                    onValueChange = { value -> fieldValueState.value = value },
                    label = { Text(text = fieldName) },
                    placeholder = { Text(text = "Enter $fieldName") },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}