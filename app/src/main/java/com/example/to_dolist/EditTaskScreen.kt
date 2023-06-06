package com.example.to_dolist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(navController: NavHostController, value: String) {
    val task by remember {
        mutableStateOf(listTask[value.toInt()])
    }
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }
    var date by remember { mutableStateOf(task.date) }
    var isTitleBlank by remember { mutableStateOf(false) }
    Column {
        TopAppBar(
            title = { Text(text = "Edit task") },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(red = 20, green = 20, blue = 20)),
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                Button(modifier = Modifier.padding(end = 14.dp),
                    onClick = {
                        if (title.isNotEmpty()) {
                            listTask[value.toInt()] = Task(title, description, date)
                            navController.navigate("home")
                        } else {
                            isTitleBlank = true
                        }
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
                value = title,
                onValueChange = { newValue ->
                    title = newValue
                    isTitleBlank = false
                },
                label = { Text(text = "Title") },
                placeholder = { Text(text = "Enter title") },
                modifier = Modifier.padding(vertical = 4.dp),
                trailingIcon = {
                    if (isTitleBlank) {
                        Icon(Icons.Default.Warning, contentDescription = "Warning", tint = Color.Red)
                    }
                }
            )
            description?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = { newValue -> description = newValue },
                    label = { Text(text = "Description") },
                    placeholder = { Text(text = "Enter description") },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            date?.let {
                OutlinedTextField(
                    value = it,
                    onValueChange = { newValue -> date = newValue },
                    label = { Text(text = "Date") },
                    placeholder = { Text(text = "Enter date") },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}