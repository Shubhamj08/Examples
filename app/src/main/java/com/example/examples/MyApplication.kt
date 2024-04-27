package com.example.examples

import android.app.Application
import com.example.examples.persistence.TaskDatabase
import com.example.examples.persistence.TaskRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application()