package com.sawelo.passdraw

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sawelo.passdraw.ui.MainViewModel
import com.sawelo.passdraw.ui.OverlayState
import com.sawelo.passdraw.ui.theme.PassDrawTheme


class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(
                        this@MainActivity,
                        "Permission is granted",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    mainViewModel.setState(OverlayState(false))
                    Toast.makeText(
                        this@MainActivity,
                        "Permission is not granted",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

        mainViewModel = MainViewModel()
        mainViewModel.observeState(this)
        setContent {
            PassDrawTheme {
                MainUi(this, requestPermissionLauncher, mainViewModel)
            }
        }
    }
}

@Composable
private fun MainUi(
    activity: Activity,
    requestPermissionLauncher: ActivityResultLauncher<String>,
    mainViewModel: MainViewModel
) {
    val isChecked = remember { mutableStateOf(false) }
    val currentState by mainViewModel.currentState.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("Enable Bubble Feature")
        Spacer(modifier = Modifier.width(15.dp))
        Switch(
            checked = currentState.currentState,
            onCheckedChange = { isChecked.value = it },
        )

//        if (isChecked.value) {
//            CheckAllPermissions(activity) {
//                RequestAllPermissions(activity, requestPermissionLauncher) {
//                    mainViewModel.setState(OverlayState(true))
//                }
//            }
//        } else mainViewModel.setState(OverlayState(false))
    }
}

//@Composable
//private fun CheckAllPermissions(activity: Activity, permissionRequired: @Composable () -> Unit) {
//    val notifRequired = if (Build.VERSION.SDK_INT >= 33) {
//        ActivityCompat.checkSelfPermission(
//            activity,
//            Manifest.permission.POST_NOTIFICATIONS
//        ) != PackageManager.PERMISSION_GRANTED
//    } else false
//    val overlayRequired = !Settings.canDrawOverlays(activity)
//    if (notifRequired && overlayRequired) permissionRequired()
//}




//@Composable
//private fun RequestAllPermissions(
//    activity: Activity,
//    requestPermissionLauncher: ActivityResultLauncher<String>,
//    onPermissionGranted: () -> Unit,
//) {
//    val openAlertDialog = remember { mutableStateOf(false) }
//    val grantedNotifDialog = remember { mutableStateOf(false) }
//    val grantedOverlayDialog = remember { mutableStateOf(false) }
//    val dismissedNotifDialog = remember { mutableStateOf(false) }
//    val dismissedOverlayDialog = remember { mutableStateOf(false) }
//
//    Log.d("MainActivity", "Check for permission")
//
//    if (Build.VERSION.SDK_INT >= 33) {
//        if (ActivityCompat.checkSelfPermission(
//                activity,
//                Manifest.permission.POST_NOTIFICATIONS
//            ) != PackageManager.PERMISSION_GRANTED && !dismissedNotifDialog.value) {
//            openAlertDialog.value = true
//            if (openAlertDialog.value) {
//                HelpUtil.CreateDialog(
//                    "PassDraw required permission for notification to work"
//                ) {
//                    openAlertDialog.value = false
//                    if (it) {
//                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//                        if (ActivityCompat.checkSelfPermission(
//                                activity,
//                                Manifest.permission.POST_NOTIFICATIONS
//                            ) != PackageManager.PERMISSION_GRANTED)
//                    }
//                    else dismissedNotifDialog.value = true
//                }
//            }
//        } else grantedNotifDialog.value = true
//    }
//
//    val overlayRequired = !Settings.canDrawOverlays(activity)
//    if (overlayRequired && !dismissedOverlayDialog.value) {
//        openAlertDialog.value = true
//        if (openAlertDialog.value) {
//            HelpUtil.CreateDialog(
//                "PassDraw required permission to display over other apps"
//            ) {
//                openAlertDialog.value = false
//                if (it) {
//                    val overlayIntent = Intent(
//                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:${activity.packageName}")
//                    )
//                    activity.startActivity(overlayIntent)
//                } else {
//                    dismissedOverlayDialog.value = true
//                }
//            }
//        }
//    } else grantedOverlayDialog.value = true
//
//    if (grantedNotifDialog.value && grantedOverlayDialog.value) onPermissionGranted()
//}


@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    PassDrawTheme {}
}