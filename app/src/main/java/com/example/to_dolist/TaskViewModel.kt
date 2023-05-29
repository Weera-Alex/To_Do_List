package com.example.to_dolist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class TaskViewModel(
    private val dao: TaskDao
): ViewModel() {
    private val _state = MutableStateFlow(TaskState())
}