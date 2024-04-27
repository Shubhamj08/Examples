package com.example.examples.notification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class SendNotificationService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when(intent?.action){
            Actions.START.toString() -> delegateTaskToWorkManager()
            Actions.STOP.toString() -> stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun delegateTaskToWorkManager(){
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>().build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }

    enum class Actions {
        START, STOP
    }

}