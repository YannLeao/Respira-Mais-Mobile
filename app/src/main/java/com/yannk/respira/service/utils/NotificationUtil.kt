package com.yannk.respira.service.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.yannk.respira.MainActivity
import com.yannk.respira.service.StopServiceReceiver

object NotificationUtil {

    const val CHANNEL_ID = "sleep_monitoring_channel"
    const val NOTIFICATION_ID = 1

    fun createNotification(context: Context): Notification {
        createNotificationChannel(context)

        // Intent para abrir o app
        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val openAppPendingIntent = PendingIntent.getActivity(
            context, 0, openAppIntent, PendingIntent.FLAG_IMMUTABLE
        )

        // Intent para parar o serviço
        val stopIntent = Intent(context, StopServiceReceiver::class.java)
        val stopPendingIntent = PendingIntent.getBroadcast(
            context, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Respira+ em execução")
            .setContentText("Monitorando sons durnte o sono")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentIntent(openAppPendingIntent)
            .setOngoing(true)
            .addAction(android.R.drawable.ic_media_pause, "Desativar", stopPendingIntent)
            .build()
    }

    private fun createNotificationChannel(context: Context) {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Monitoramento do Sono",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }
}
