package com.sawelo.passdraw.service.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.sawelo.passdraw.R

class FloatingBubbleNotification(
    private val lifecycleService: LifecycleService,
) {
    private fun createChannel() {
        val name = "Floating Bubble Channel"
        val descriptionText = "Channel for adjusting floating bubble"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager = lifecycleService.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotificationBuilder(): Notification {
        createChannel()
        val largeIcon =
            BitmapFactory.decodeResource(lifecycleService.resources, R.drawable.ic_stat_name)
        return NotificationCompat.Builder(lifecycleService, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setLargeIcon(largeIcon)
            .setContentTitle("Tap to draw")
            .setContentText("Tap here to open drawing dialog")
            .setShowWhen(false)
//            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
//            .setContentIntent(openDialogPendingIntent())
//            .addAction(
//                R.drawable.baseline_visibility_24,
//                if (isFloatingBubbleUnwrapped)
//                    "Hide floating bubble" else "Show floating bubble",
//                wrapServicePendingIntent(isFloatingBubbleUnwrapped)
//            )
            .build()
    }

    fun startForegroundNotification() {


        Log.d("FloatingBubbleNotification", "Starting notification")
        lifecycleService.startForeground(NOTIFICATION_ID, createNotificationBuilder())
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "FLOATING_BUBBLE_CHANNEL_ID"
    }

}