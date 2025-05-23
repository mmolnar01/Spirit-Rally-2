package hu.klm60o.android.spiritrally2.permissions

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import hu.klm60o.android.spiritrally2.components.MyAlertDialog

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestCameraAndLocationPermissionDialog(
    granted: Boolean,
    onGranted: (Boolean) -> Unit
) {
    val permissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.CAMERA
        )
    )

    if (permissionsState.allPermissionsGranted) {
        onGranted
    } else {
        if (permissionsState.shouldShowRationale) {
            CameraAndLocationRationaleDialog()
        } else {
            CameraAndLocationPermissionDialog { permissionsState.launchMultiplePermissionRequest() }
        }
    }

    /*if (!permissionsState.allPermissionsGranted) {
        if (permissionsState.shouldShowRationale) {
            CameraAndLocationRationaleDialog()
        } else {
            CameraAndLocationPermissionDialog { permissionsState.launchMultiplePermissionRequest() }
        }
    }*/
}

@Composable
fun CameraAndLocationRationaleDialog() {
    val context = LocalContext.current
    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        MyAlertDialog(
            onConfirmation = {
                showWarningDialog = false

                //Megnyitjuk a beállításokat
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.packageName, null)
                )
                context.startActivity(intent)
            },
            onDismissRequest = {},
            dialogTitle = "Jogosultság engedélyezése",
            dialogText = "Az alkalmazás nem működik jogosultságok nélkül. Kérlek engedélyezd: Kamera, Helyzet",
            icon = Icons.Filled.Info
        )
    }
}


@Composable
fun CameraAndLocationPermissionDialog(onRequestPermission: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        AlertDialog(
            icon = {
                Icon(Icons.Filled.Info, contentDescription = "Pop-up Icon")
            },
            title = {
                Text(text = "Jogosultság engedélyezése")
            },
            text = {
                Text(text = "Kérlek engedélyezd a felugró jogosultságokat")
            },
            onDismissRequest = {

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onRequestPermission()
                        showWarningDialog = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}