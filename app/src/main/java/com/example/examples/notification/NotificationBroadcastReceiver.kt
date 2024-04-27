package com.example.examples.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.example.examples.R
import com.example.examples.persistence.PersistenceActivity

class NotificationBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if(context != null && intent?.action == context.getString(R.string.foreground_notification_click_action)){

            Intent(context, PersistenceActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(it)
            }

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.cancel(NotificationWorker.NOTIFICATION_ID)
        }

    }
}