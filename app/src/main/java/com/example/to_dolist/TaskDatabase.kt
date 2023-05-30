package com.example.to_dolist

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {
    abstract val dao: TaskDao
    companion object {
        const val NAME = "todos"
    }
}