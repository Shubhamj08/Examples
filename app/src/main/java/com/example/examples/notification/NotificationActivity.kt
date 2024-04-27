package com.example.examples.notification

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.examples.ui.theme.ExamplesTheme

class NotificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        setContent {
            ExamplesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotificationUI(triggerNotification = {
                        triggerNotificationService()
                    })
                }
            }
        }
    }

    private fun triggerNotificationService() {
        Intent(this, SendNotificationService::class.java).also {
            it.action = SendNotificationService.Actions.START.toString()
            startService(it)
        }
    }
}

@Composable
fun NotificationUI(triggerNotification: () -> Unit) {
    Button(
        onClick = { triggerNotification() },
        modifier = Modifier
            .padding(4.dp)
            .wrapContentSize(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text("Send Notification")
    }
}