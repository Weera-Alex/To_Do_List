package com.example.to_dolist

sealed interface TaskEvent {
    object SaveTask: TaskEvent
    data class SetTask(val task: String) : TaskEvent
    data class SetDescription(val description: String) : TaskEvent
    data class SetDate(val date: String) : TaskEvent
    data class SetStatus(val status: Boolean = false) : TaskEvent
}