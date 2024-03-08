package com.sawelo.passdraw.service

import android.content.Intent
import androidx.lifecycle.LifecycleService
import com.sawelo.passdraw.service.notification.FloatingBubbleNotification
import com.sawelo.passdraw.util.Constants.NOTIFICATION_START_ACTION
import com.sawelo.passdraw.util.Constants.NOTIFICATION_STOP_ACTION
import com.sawelo.passdraw.window.FloatingBubbleWindow

class NotificationFloatingBubbleService: LifecycleService() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val floatingBubbleNotification = FloatingBubbleNotification(this)
        println("Current intent: ${intent?.action}")
        when (intent?.action) {
            NOTIFICATION_START_ACTION -> {
                floatingBubbleNotification.startForegroundNotification()
                FloatingBubbleWindow(this).showWindow()
            }
            NOTIFICATION_STOP_ACTION -> stopSelf()
            else -> throw Exception("Intent action is not recognizable")
        }
        return START_STICKY
    }


}