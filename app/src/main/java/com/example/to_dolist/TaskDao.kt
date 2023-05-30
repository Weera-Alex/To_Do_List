package com.example.to_dolist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface TaskDao {
    // suspend keyword to let it run on a separate thread.
    @Insert
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM Task WHERE status = :status")
    fun getTasksByStatus(status: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<Task>>
}