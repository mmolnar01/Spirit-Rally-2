package hu.klm60o.android.spiritrally2.presentation.userdata.components

import android.Manifest
import android.content.ContentValues.TAG
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import hu.klm60o.android.spiritrally2.assets.QrCode
import hu.klm60o.android.spiritrally2.domain.model.UserData
import hu.klm60o.android.spiritrally2.permissions.RequestCameraAndLocationPermissionDialog
import hu.klm60o.android.spiritrally2.useful.checkIsInBound
import hu.klm60o.android.spiritrally2.useful.showToast

@Composable
fun AddUserDataFloatingActionButton(
    onAddUserData: (userData: UserData) -> Unit
) {
    val context = LocalContext.current

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
                //Rajtszám, aztán kategória
                //showToast(context, result.contents)
                val ints = result.contents.split(";").map { it.toInt() }
                if (ints.size == 2) {
                    val newUserData = UserData(
                        id = Firebase.auth.currentUser?.uid,
                        number = ints[0],
                        category = ints[1]
                    )
                    onAddUserData(newUserData)
                }
                //showToast(context, ints[0].toString())
            } catch (e: Exception) {
                showToast(context, e.toString())
                Log.e(TAG, e.toString())
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

    FloatingActionButton(
        onClick = {
            showCamera()
        },
        containerColor = MaterialTheme.colorScheme.primary,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(10.dp, 15.dp, 15.dp,15.dp)
    ) {
        Icon(QrCode, contentDescription = "Read QR code")
    }


}