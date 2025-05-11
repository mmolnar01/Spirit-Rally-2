package hu.klm60o.android.spiritrally2.presentation.racepoints.components

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.firebase.Timestamp
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import hu.klm60o.android.spiritrally2.assets.QrCode
import hu.klm60o.android.spiritrally2.domain.model.Racepoint
import hu.klm60o.android.spiritrally2.useful.checkIsInBound
import hu.klm60o.android.spiritrally2.useful.showToast
import java.util.Calendar

@SuppressLint("MissingPermission")
@Composable
fun AddRacepointFloatingActionButton(
    onEditRacepoint: (String, Timestamp) -> Unit,
    racepointList: List<Racepoint>
) {
    val calendar = Calendar.getInstance()
    val context = LocalContext.current
    //var textResultInteger by remember { mutableStateOf("") }
    var resultInteger by remember { mutableIntStateOf(0) }
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    //Elindítjuk a barcode olvasást
    val barCodeLauncher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result ->

        //Ha nincs eredmény
        if (result.contents == null) {
            showToast(context, "Beolvasás megszakítva")
        } else {
            //textResultInteger = result.contents.toIntOrNull().toString()

            //Try blokk az eredmény kiolvasásánál
            //Így el tudjuk kapni a NumberFormatException-t
            try {
                resultInteger = result.contents.toInt()

                //Kiolvassuk a listából a Racepoint-ot, és ha nem null, megyünk tovább
                val racepoint = racepointList.getOrNull(resultInteger - 1)
                if (racepoint != null) {
                    val cancellationTokenSource = CancellationTokenSource()
                    fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token)
                        .addOnSuccessListener { currentLocation ->
                            val racepointLocation = Location("Current Racepoint Location")
                            racepointLocation.latitude = racepoint.location?.latitude ?: 0.0
                            racepointLocation.longitude = racepoint.location?.longitude ?: 0.0

                            if (currentLocation.checkIsInBound(100.0, racepointLocation)) {
                                onEditRacepoint(resultInteger.toString(), Timestamp(calendar.time))
                            } else {
                                showToast(context, "Rossz helyen vagy, menj közelebb az ellenőrző ponthoz")
                            }
                    }
                } else {
                    showToast(context, "Nincs ilyen ellenőrző pont")
                }
            } catch (e: NumberFormatException) {
                showToast(context, "Helytelen bemenet")
            }
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

