package hu.klm60o.android.spiritrally2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.klm60o.android.spiritrally2.screens.MapScreenComposable
import hu.klm60o.android.spiritrally2.screens.NewsScreenComposable
import hu.klm60o.android.spiritrally2.screens.ProfileScreenComposable
import hu.klm60o.android.spiritrally2.screens.ResultScreenComposable
import hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme
import hu.klm60o.android.spiritrally2.useful.registerUser
import hu.klm60o.android.spiritrally2.useful.showToast
import kotlinx.serialization.Serializable

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