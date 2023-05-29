package com.example.to_dolist

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val title: String = "",
    val description: String? = null,
    val date: String? = null,
    val status: Boolean = false,
)
