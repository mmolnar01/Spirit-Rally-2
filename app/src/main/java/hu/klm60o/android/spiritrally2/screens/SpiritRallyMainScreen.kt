package hu.klm60o.android.spiritrally2.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import hu.klm60o.android.spiritrally2.navigation.NavigationItem
import hu.klm60o.android.spiritrally2.permissions.RequestCameraAndLocationPermissionDialog
import hu.klm60o.android.spiritrally2.permissions.RequestNotificationCameraLocationPermissionDialog
import hu.klm60o.android.spiritrally2.presentation.userdata.components.SetUserLocation
import kotlinx.serialization.Serializable

//Main screen, amely tartalmazza a többi screent
//A helyzet trackelése miatt szükséges, hogy mindig "képernyőn legyen"
@SuppressLint("MissingPermission")
@Composable
fun SpiritRallyMainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    var locationTrackingChecked by rememberSaveable { mutableStateOf(false) }
    var permissionsGranted by rememberSaveable { mutableStateOf(false) }
    val navigationItems = listOf(
        NavigationItem(
            title = "Hírek",
            icon = Icons.Default.Notifications,
            route = NewsScreen
        ),
        NavigationItem(
            title = "Térkép",
            icon = Icons.Default.LocationOn,
            route = MapScreen
        ),
        NavigationItem(
            title = "Eredmények",
            icon = Icons.Default.Info,
            route = ResultScreen
        ),
        NavigationItem(
            title = "Profil",
            icon = Icons.Default.Person,
            route = ProfileScreen
        )
    )
    Scaffold(
        bottomBar = { MyBottomAppbarComposable(navController, navigationItems) },
        topBar = { MyTopAppBar() }
    ) { innerPadding ->
        //Jogosultságok lekérdezése
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            RequestNotificationCameraLocationPermissionDialog(permissionsGranted) { permissionsGranted = it }
        } else {
            RequestCameraAndLocationPermissionDialog(permissionsGranted) { permissionsGranted = it }
        }

        var locationRequest by rememberSaveable {
            mutableStateOf<LocationRequest?>(null)
        }

        locationRequest = LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 10000).build()

        if (locationRequest != null && locationTrackingChecked && permissionsGranted) {
            SetUserLocation(locationRequest!!)
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
                    ProfileScreenComposable(navController = navController, checked = locationTrackingChecked) { locationTrackingChecked = it }
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