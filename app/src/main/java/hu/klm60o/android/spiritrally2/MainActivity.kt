package hu.klm60o.android.spiritrally2

import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import hu.klm60o.android.spiritrally2.presentation.userdata.UserDataViewModel
import hu.klm60o.android.spiritrally2.screens.MapScreenComposable
import hu.klm60o.android.spiritrally2.screens.NewsScreenComposable
import hu.klm60o.android.spiritrally2.screens.ProfileScreenComposable
import hu.klm60o.android.spiritrally2.screens.ResultScreenComposable
import hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme
import kotlinx.serialization.Serializable
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.ktx.auth
import hu.klm60o.android.spiritrally2.components.LoadingIndicator
import hu.klm60o.android.spiritrally2.domain.model.Response
import hu.klm60o.android.spiritrally2.presentation.userdata.components.EmptyUserDataContent
import hu.klm60o.android.spiritrally2.presentation.userdata.components.SetUserLocation
import hu.klm60o.android.spiritrally2.presentation.userdata.components.UserDataContent
import hu.klm60o.android.spiritrally2.useful.showToast

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpiritRally2Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = NewsScreen) {
                    composable<NewsScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            NewsScreenComposable(navController = navController)
                        }
                    }
                    composable<MapScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            MapScreenComposable(navController = navController)
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

        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@addOnSuccessListener
            val db = FirebaseFirestore.getInstance()
            db.collection("tokens").document(userId)
                .set(mapOf("token" to token))
                .addOnSuccessListener { Log.d("FCM", "Token saved") }
                .addOnFailureListener { e -> Log.w("FCM", "Error saving token", e)}
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