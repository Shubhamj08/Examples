package com.example.examples.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.examples.R
import com.example.examples.persistence.PersistenceActivity

class NotificationWorker(
    context: Context,
    params: WorkerParameters
):
    CoroutineWorker(context, params) {

    companion object {
        const val CHANNEL_ID = "ForegroundServiceChannel"
        const val NOTIFICATION_ID = 1
    }
    override suspend fun doWork(): Result {
        startSendingNotification()
        return Result.success()
    }

    @SuppressLint("MissingPermission")
    private fun startSendingNotification() {

        createNotificationChannel()

        val notificationIntent = Intent(applicationContext, NotificationBroadcastReceiver::class.java).apply {
            action = applicationContext.getString(R.string.foreground_notification_click_action)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val activityIntent = Intent(applicationContext, PersistenceActivity::class.java).apply {
            action = applicationContext.getString(R.string.foreground_notification_click_action)
        }
        val activityPendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationAction = NotificationCompat.Action.Builder(
            R.drawable.ic_notification,
            "Open",
            pendingIntent
        ).build()

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID).apply {
            setContentTitle("Foreground Service Example")
            setContentText("Foreground service is running")
            setSmallIcon(R.drawable.ic_notification)
            addAction(notificationAction)
            setContentIntent(activityPendingIntent)
            setAutoCancel(true)
        }.build()

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(NOTIFICATION_ID, notification)

    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Foreground Service Channel"
            val descriptionText = "Channel for foreground service"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}