package hu.klm60o.android.spiritrally2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import hu.klm60o.android.spiritrally2.screens.SpiritRallyMainScreen
import hu.klm60o.android.spiritrally2.ui.theme.SpiritRally2Theme
import hu.klm60o.android.spiritrally2.useful.showToast

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var backPressedTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpiritRally2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SpiritRallyMainScreen()
                }
            }
        }

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
               if (backPressedTime + 3000L > System.currentTimeMillis()){
                   finish()
               } else {
                   showToast(this@MainActivity, "Nyomd meg újra a vissza gombot a kilépéshez")
               }
                backPressedTime = System.currentTimeMillis()
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)

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

/*@Serializable
object NewsScreen

@Serializable
object MapScreen

@Serializable
object ResultScreen

@Serializable
object ProfileScreen*/