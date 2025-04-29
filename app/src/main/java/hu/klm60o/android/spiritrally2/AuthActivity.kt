package hu.klm60o.android.spiritrally2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import hu.klm60o.android.spiritrally2.screens.LoginScreenComposable
import hu.klm60o.android.spiritrally2.screens.RegisterScreenComposable
import hu.klm60o.android.spiritrally2.screens.ResetPasswordScreenComposable
import hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
        enableEdgeToEdge()
        setContent {
            SpiritRally2Theme {
                //NavController létrehozása
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = LoginScreen) {
                    composable<LoginScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            LoginScreenComposable(navController = navController)
                        }
                    }
                    composable<RegisterScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            RegisterScreenComposable(navController = navController)
                        }
                    }
                    composable<ResetPasswordScreen> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            ResetPasswordScreenComposable(navController = navController)
                        }
                    }
                }

                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    SpiritRally2Theme {
        Greeting2("Android")
    }
}

@Serializable
object LoginScreen

@Serializable
object RegisterScreen

@Serializable
object ResetPasswordScreen