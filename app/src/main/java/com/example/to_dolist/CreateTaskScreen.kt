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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(navController: NavHostController, state: TaskState, onEvent: (TaskEvent) -> Unit) {


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
                        onEvent(TaskEvent.SaveTask)
                        navController.navigateUp()
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
                OutlinedTextField(
                    value = state.title,
                    onValueChange = { onEvent(TaskEvent.SetTitle(it)) },
                    label = { Text(text = "Title") },
                    placeholder = { Text(text = "Enter title") },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                OutlinedTextField(
                    value = state.description  ?: "",
                    onValueChange = { onEvent(TaskEvent.SetDescription(it)) },
                    label = { Text(text = "Description") },
                    placeholder = { Text(text = "Enter description") },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                OutlinedTextField(
                    value = state.date ?: "",
                    onValueChange = { onEvent(TaskEvent.SetDate(it)) },
                    label = { Text(text = "Date") },
                    placeholder = { Text(text = "Enter date") },
                    modifier = Modifier.padding(vertical = 4.dp)
                )

        }
    }
}