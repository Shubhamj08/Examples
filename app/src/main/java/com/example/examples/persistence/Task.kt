package com.example.examples.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey


enum class Priority { LOW, MEDIUM, HIGH }

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val text: String,
    val priority: Priority,
    var isCompleted: Boolean = false
)
