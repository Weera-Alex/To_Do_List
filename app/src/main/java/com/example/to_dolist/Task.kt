package com.example.to_dolist

import androidx.compose.runtime.mutableStateListOf

data class Task(
    val title: String,
    val description: String,
    val date: String,
)

val listTask = mutableStateListOf<Task>()
