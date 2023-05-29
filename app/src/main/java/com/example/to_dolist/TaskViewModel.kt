package com.example.to_dolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(
    private val dao: TaskDao
): ViewModel() {
    private val _state = MutableStateFlow(TaskState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskState())

    fun onEvent(event: TaskEvent) {
        when(event) {
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    dao.deleteTask(event.task)
                }
            }
            TaskEvent.SaveTask -> {
                val title = state.value.title
                val description = state.value.description
                val date = state.value.date
                val status = state.value.status
                if(title.isBlank() || description.isBlank() || date.isBlank()) {
                    return
                }

                val task = Task(
                    title = title,
                    description = description,
                    date = date,
                    status = status
                )
                viewModelScope.launch {
                    dao.insertTask(task)
                }
            }
            is TaskEvent.SetDate -> {
                _state.update {
                    it.copy(
                        date = event.date
                    )
                }
            }
            is TaskEvent.SetDescription -> _state.update {
                it.copy(
                    description = event.description
                )
            }
            is TaskEvent.SetStatus -> _state.update {
                it.copy(
                    status = event.status
                )
            }
            is TaskEvent.SetTitle -> _state.update {
                it.copy(
                    title = event.title
                )
            }
        }
    }
}