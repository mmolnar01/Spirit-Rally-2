package hu.klm60o.android.spiritrally2.presentation.userdata.components

import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import hu.klm60o.android.spiritrally2.presentation.userdata.UserDataViewModel
import android.Manifest
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.firebase.firestore.GeoPoint
import hu.klm60o.android.spiritrally2.components.LoadingIndicator
import hu.klm60o.android.spiritrally2.domain.model.Response
import hu.klm60o.android.spiritrally2.useful.showToast


@RequiresPermission(
    anyOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION],
)

@Composable
fun SetUserLocation(
    locationRequest: LocationRequest,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: UserDataViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val editUserDataResponse by viewModel.editUserDataState.collectAsStateWithLifecycle()

    when(val editUserDataResponse = editUserDataResponse) {
        is Response.Idle -> {}
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            showToast(context, "Helyzet frissítve")
            viewModel.resetEditUserDataState()
        }
        is Response.Failure -> editUserDataResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                showToast(context, errorMessage)
                viewModel.resetEditUserDataState()
            }
        }
    }

    DisposableEffect(locationRequest, lifecycleOwner) {
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        val cancellationTokenSource = CancellationTokenSource()
        locationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token)
        //showToast(context, "ECCER")

        val locationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.locations.last().let { location ->
                    val userDataUpdate = mapOf("location" to GeoPoint(location.latitude, location.longitude))
                    viewModel.editUserData(userDataUpdate)
                }
                /*result.lastLocation?.let { location ->
                    val userDataUpdate = mapOf("location" to GeoPoint(location.latitude, location.longitude))
                    viewModel.editUserData(userDataUpdate)
                }*/
            }
        }

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                locationClient.requestLocationUpdates(
                    locationRequest, locationCallback, Looper.getMainLooper()
                )
            } else if (event == Lifecycle.Event.ON_STOP) {
                locationClient.removeLocationUpdates(locationCallback)
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            locationClient.removeLocationUpdates(locationCallback)
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}