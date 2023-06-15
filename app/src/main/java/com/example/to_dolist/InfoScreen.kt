package com.example.to_dolist


import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(navController: NavHostController, value: String) {
    val task by remember {
        mutableStateOf(listTask[value.toInt()])
    }
    val title by remember { mutableStateOf(task.title) }
    Column {
        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { navController.navigate("edit/$value") }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { /* Handle more options click */ }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More")
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(red = 20, green = 20, blue = 20)
            )
        )
        task.description?.let { Text(text = it) }
        task.date?.let { Text(text = it.toString()) }
    }
}