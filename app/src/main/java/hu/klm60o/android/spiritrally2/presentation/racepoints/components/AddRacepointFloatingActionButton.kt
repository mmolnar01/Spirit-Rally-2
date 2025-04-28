package hu.klm60o.android.spiritrally2.presentation.racepoints.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import hu.klm60o.android.spiritrally2.assets.QrCode
import hu.klm60o.android.spiritrally2.useful.showToast
import java.util.Calendar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember

@Composable
fun AddRacepointFloatingActionButton(
    onEditRacepoint: (String, Timestamp) -> Unit
) {
    val calendar = Calendar.getInstance()
    val context = LocalContext.current
    var textResultInteger by remember { mutableStateOf("") }

    val barCodeLauncher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result ->
        if (result.contents == null) {
            showToast(context, "Beolvasás megszakítva")
        } else {
            textResultInteger = result.contents.toIntOrNull().toString()
            //showToast(context, textResultInteger)
            onEditRacepoint(textResultInteger, Timestamp(calendar.time))
        }
    }


    fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Olvass be egy QR kódot")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setOrientationLocked(false)

        barCodeLauncher.launch(options)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (!permissions.containsValue(false)) {
            showCamera()
        } else {
            showToast(context, "Kérem engedélyezze a jogosultságokat")
        }
    }

    FloatingActionButton(
        onClick = {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.primary,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(10.dp, 15.dp, 15.dp,15.dp)
    ) {
        Icon(QrCode, contentDescription = "Read QR code")
    }
}

