package hu.klm60o.android.spiritrally2.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.klm60o.android.spiritrally2.permissions.RequestCameraAndLocationPermissionDialog
import hu.klm60o.android.spiritrally2.permissions.RequestNotificationPermissionDialog
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import hu.klm60o.android.spiritrally2.presentation.userdata.components.SetUserLocation
import hu.klm60o.android.spiritrally2.useful.showToast

@Composable
fun SpiritRallyMainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    Scaffold(
        bottomBar = { MyBottomAppbarComposable(navController) },
        topBar = { MyTopAppBar() }
    ) { innerPadding ->

        //Jogosultságok lekérdezése
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            RequestNotificationPermissionDialog()
        }
        RequestCameraAndLocationPermissionDialog()

        var locationRequest by rememberSaveable {
            mutableStateOf<LocationRequest?>(null)
        }

        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).build()

        if (locationRequest != null) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                SetUserLocation(locationRequest!!)
            }
        }

        NavHost(navController = navController, startDestination = NewsScreen, modifier = Modifier.padding(innerPadding)) {
            composable<NewsScreen> {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewsScreenComposable(navController = navController, innerPadding = innerPadding)
                }
            }
            composable<MapScreen> {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MapScreenComposable(navController = navController, innerPadding = innerPadding)
                }
            }
            composable<ResultScreen> {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ResultScreenComposable(navController = navController)
                }
            }
            composable<ProfileScreen> {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProfileScreenComposable(navController = navController)
                }
            }
        }
    }
}

@Serializable
object NewsScreen

@Serializable
object MapScreen

@Serializable
object ResultScreen

@Serializable
object ProfileScreen