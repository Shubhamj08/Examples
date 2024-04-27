package com.example.examples.persistence

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {

    val tasks: Flow<List<Task>> = taskDao.getTasks()

    @WorkerThread
    suspend fun addTask(task: Task){
        taskDao.addTask(task)
    }

    @WorkerThread
    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    @WorkerThread
    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }

}