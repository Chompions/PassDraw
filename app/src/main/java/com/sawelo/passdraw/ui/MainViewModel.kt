package com.sawelo.passdraw.ui

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sawelo.passdraw.service.NotificationFloatingBubbleService
import com.sawelo.passdraw.util.Constants
import com.sawelo.passdraw.util.Constants.NOTIFICATION_STOP_ACTION
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _currentState = MutableStateFlow(OverlayState())
    val currentState: StateFlow<OverlayState> = _currentState.asStateFlow()

    fun setState(overlayState: OverlayState) {
        _currentState.value = overlayState
    }

    fun observeState(context: Context) {
        viewModelScope.launch {
            currentState.collect {
                if (it.currentState) {
                    startService(context)
                    Toast.makeText(context, "Bubble is Activated", Toast.LENGTH_SHORT).show()
                } else {
                    stopService(context)
                    Toast.makeText(context, "Bubble is Deactivated", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startService(context: Context) {
        val serviceIntent = Intent(context, NotificationFloatingBubbleService::class.java)
        serviceIntent.action = Constants.NOTIFICATION_START_ACTION
        Log.d("NotificationFloatingBubbleService", "Starting service")
        context.startForegroundService(serviceIntent)
    }

    private fun stopService(context: Context) {
        val serviceIntent = Intent(context, NotificationFloatingBubbleService::class.java)
        serviceIntent.action = NOTIFICATION_STOP_ACTION
        Log.d("NotificationFloatingBubbleService", "Stopping service")
        context.stopService(serviceIntent)
    }
}