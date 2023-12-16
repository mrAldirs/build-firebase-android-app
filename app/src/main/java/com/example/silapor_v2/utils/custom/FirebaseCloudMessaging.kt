package com.example.silapor_v2.utils.custom

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseCloudMessaging : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM Token", "FCM Token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = "Informasi!"
        val body = "Anda berhasil login!"
        val clickAction = remoteMessage.data["click_action"]

        createNotification(title, body, clickAction)
    }

    private fun createNotification(title: String?, body: String?, clickAction: String?) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "YourChannelId"
            val channel = NotificationChannel(channelId, "YourChannelName", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "YourChannelDescription"
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationId = System.currentTimeMillis().toInt()
        val notificationBuilder = NotificationCompat.Builder(this, "YourChannelId")
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setAutoCancel(true)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}