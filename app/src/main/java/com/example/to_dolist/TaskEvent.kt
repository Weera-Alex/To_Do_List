package com.example.to_dolist

sealed interface TaskEvent {
    object SaveTask: TaskEvent
    object GetAllTasks : TaskEvent
    data class SetTitle(val title: String) : TaskEvent
    data class SetDescription(val description: String? = null) : TaskEvent
    data class SetDate(val date: String? = null) : TaskEvent
    data class SetStatus(val status: Boolean = false) : TaskEvent
    data class DeleteTask(val task: Task): TaskEvent
}