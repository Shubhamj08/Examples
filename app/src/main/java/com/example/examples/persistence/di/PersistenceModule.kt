package com.example.examples.persistence.di

import android.app.Application
import android.content.Context
import com.example.examples.persistence.TaskDao
import com.example.examples.persistence.TaskDatabase
import com.example.examples.persistence.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideTaskDao(context: Application): TaskDao {
        return TaskDatabase.getDatabase(context).taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(dao: TaskDao): TaskRepository {
        return TaskRepository(dao)
    }
}