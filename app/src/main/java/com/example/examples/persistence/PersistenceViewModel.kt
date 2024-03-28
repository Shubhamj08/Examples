package com.example.examples.persistence

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel

class PersistenceViewModel: ViewModel() {

    private var _text: TextFieldValue by mutableStateOf(TextFieldValue())
    val text: TextFieldValue
        get() = _text

    private var _priority: Priority by mutableStateOf(Priority.MEDIUM)
    val priority: Priority
        get() = _priority


    private var _tasks: List<Task> by mutableStateOf(listOf(
        Task(1, "Task 1", Priority.LOW),
        Task(2, "Task 2", Priority.MEDIUM, true),
        Task(3, "Task 3", Priority.HIGH),
    ))
    val tasks: List<Task>
        get() = _tasks

    fun handleTextChange(textValue: TextFieldValue) {
        _text = textValue
    }

    fun handlePriorityChange(value: Priority){
        _priority = value
    }

    fun handleCompletedCheckbox(task: Task, isChecked: Boolean){
        _tasks = _tasks.map { item ->
            if(item == task) item.copy(isCompleted = isChecked)
            else item
        }
    }

}