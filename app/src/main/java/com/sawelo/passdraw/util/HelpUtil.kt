package com.sawelo.passdraw.util

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

object HelpUtil {
    @Composable
    fun CreateDialog(message: String, onClick: (Boolean) -> Unit) {
        AlertDialog(
            title = {
                Text("Permission required")
            },
            text = {
              Text(message)
            },
            onDismissRequest = {
                onClick.invoke(false)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClick.invoke(true)
                    }
                ) {
                    Text("Grant permission")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onClick.invoke(false)
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}