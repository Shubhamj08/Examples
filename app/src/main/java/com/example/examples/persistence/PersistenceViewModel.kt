package com.example.examples.persistence

import android.database.sqlite.SQLiteException
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PersistenceViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }
    }

    var text: TextFieldValue by mutableStateOf(TextFieldValue())
        private set

    var priority: Priority by mutableStateOf(Priority.MEDIUM)
        private set


    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasksState: StateFlow<List<Task>>
    get() = _tasks


    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            taskRepository.tasks.collect {
                _tasks.value = it
            }
        }
    }

    fun handleTextChange(textValue: TextFieldValue) {
        text = textValue
    }

    fun handlePriorityChange(value: Priority){
        priority = value
    }

    fun handleCompletedCheckbox(task: Task, isChecked: Boolean){
        lateinit var updatedTask: Task
        _tasks.value = _tasks.value.map { item ->
            if(item == task) item.copy(isCompleted = isChecked).also { updatedTask = it }
            else item
        }

        GlobalScope.launch(Dispatchers.IO + exceptionHandler) {
            taskRepository.updateTask(updatedTask)
        }
    }

    fun handleSaveTask(){
        val task = Task(text = text.text, priority = priority, isCompleted = false)

        GlobalScope.launch(Dispatchers.IO + exceptionHandler) {
            taskRepository.addTask(task)

            withContext(Dispatchers.Main){
                text = TextFieldValue("")
            }
        }
    }

    fun handleDeleteTask(task: Task) {
        GlobalScope.launch(Dispatchers.IO + exceptionHandler) {
            taskRepository.deleteTask(task)
        }
    }

}