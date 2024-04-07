package com.example.examples.persistence

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Insert
    fun addTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("SELECT * FROM task " +
            "ORDER BY CASE WHEN isCompleted = 1 THEN 1 ELSE 0 END, " +
            "CASE priority " +
            "WHEN 'HIGH' THEN 0 " +
            "WHEN 'MEDIUM' THEN 1 " +
            "WHEN 'LOW' THEN 2 " +
            "ELSE 3 " +
            "END")
    fun getTasks(): Flow<List<Task>>

}



@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {

        @Volatile
        private var INSTANCE: TaskDatabase? = null;

        fun getDatabase(context: Context): TaskDatabase {

            return INSTANCE ?: synchronized(this) {
                Room
                    .databaseBuilder(context.applicationContext, TaskDatabase::class.java, "task_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}