package com.example.examples.persistence


enum class Priority { LOW, MEDIUM, HIGH }
data class Task(
    val id: Int?,
    val text: String,
    val priority: Priority,
    var isCompleted: Boolean = false
)
