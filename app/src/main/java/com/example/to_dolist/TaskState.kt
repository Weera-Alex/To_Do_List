package com.example.to_dolist

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val status: Boolean = false,
)
