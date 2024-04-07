package com.example.examples

import android.app.Application
import com.example.examples.persistence.TaskDatabase
import com.example.examples.persistence.TaskRepository

class MyApplication: Application() {

    private val taskDb by lazy { TaskDatabase.getDatabase(this) }
    val repository by lazy { TaskRepository(taskDb.taskDao()) }

}