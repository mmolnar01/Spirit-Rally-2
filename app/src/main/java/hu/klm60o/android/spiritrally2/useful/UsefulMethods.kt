package hu.klm60o.android.spiritrally2.useful

import android.content.Context
import android.util.Patterns
import android.widget.Toast
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