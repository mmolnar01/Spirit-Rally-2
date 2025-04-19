package hu.klm60o.android.spiritrally2.useful

import android.content.ContentValues.TAG
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlin.math.round

fun showToast(context: Context, msg: String) {
    Toast.makeText(
        context,
        msg,
        Toast.LENGTH_SHORT
    ).show()
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

//Ellenőrízzük az email címet
fun validateEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

//Ellenőrízzük a jelszót
fun validatePassword(password: String): Boolean {
    return password.length >= 5
}

//Ellenőrízzük, hogy a két beírt jelszó egyezik-e
fun validatePasswordRepeat(password: String, passwordRepeat: String): Boolean {
    return password.equals(passwordRepeat)
}

//Regisztráció
fun registerUser(email: String, password: String, onResult: (Throwable?) -> Unit) {
    Firebase.auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { onResult(it.exception) }
}

//Bejelentkezés
fun loginUSer(email: String, password: String, onResult: (Throwable?) -> Unit) {
    Firebase.auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { onResult(it.exception) }
}

fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun setDisplayName(name: String) {
    val user = Firebase.auth.currentUser

    val profileUpdates = userProfileChangeRequest {
        displayName = name
    }

    user!!.updateProfile(profileUpdates)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "User profile updated.")
            }
        }
}