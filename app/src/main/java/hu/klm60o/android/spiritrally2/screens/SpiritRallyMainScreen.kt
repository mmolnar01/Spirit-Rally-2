package hu.klm60o.android.spiritrally2.screens

import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.klm60o.android.spiritrally2.permissions.RequestCameraAndLocationPermissionDialog
import hu.klm60o.android.spiritrally2.permissions.RequestNotificationPermissionDialog
import kotlinx.serialization.Serializable

@Composable
fun SpiritRallyMainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { MyBottomAppbarComposable(navController) },
        topBar = { MyTopAppBar() }
    ) { innerPadding ->

        //Jogosultságok lekérdezése
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            RequestNotificationPermissionDialog()
        }
        RequestCameraAndLocationPermissionDialog()

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