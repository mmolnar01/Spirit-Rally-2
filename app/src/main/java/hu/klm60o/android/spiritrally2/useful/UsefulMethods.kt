package hu.klm60o.android.spiritrally2.useful

import android.content.ContentValues.TAG
import android.content.Context
import android.content.ContextWrapper
import android.location.Location
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import org.osmdroid.util.GeoPoint
import com.google.firebase.ktx.Firebase
import io.ticofab.androidgpxparser.parser.GPXParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sqrt

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

//Jelszó megváltoztatása
fun resetUserPassword(email: String, onResult: (Throwable?) -> Unit) {
    Firebase.auth.sendPasswordResetEmail(email)
        .addOnCompleteListener { onResult(it.exception) }
}

//Activity megkeresése Context alapján
fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

//Felhasználónév beállítása
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

//Megnézi, hogy "this"-hez képest "center" "radius" távolságon belül van-e
fun Location.checkIsInBound(radius: Double, center: Location): Boolean = this.distanceTo(center) < radius

//Kiszámítja a távolságot két koordináta pár között
fun getDistanceFromLatLonInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371.0
    val dLat = deg2rad(lat2 - lat1)
    val dLon = deg2rad(lon2 - lon1)

    val a = sin(dLat / 2).pow(2) +
            cos(deg2rad(lat1)) *
            cos(deg2rad(lat2)) *
            sin(dLon / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return R * c
}

fun deg2rad(deg: Double): Double {
    return deg * (Math.PI / 180)
}

//Lekérdezi a GeopPoint-okat a GPX fájlból
fun getGeoPointsFromGpx(context: Context, fileName: String): MutableList<GeoPoint> {
    val parser = GPXParser()
    val geoPoints: MutableList<GeoPoint> = ArrayList<GeoPoint>()
    try {
        val input: InputStream = context.assets.open(fileName)
        val parsedGpx = parser.parse(input)
        parsedGpx?.let {
            parsedGpx.tracks.forEach { track ->
                track.trackSegments.forEach { trackSegment ->
                    trackSegment.trackPoints.forEach { trackPoint ->
                        geoPoints.add(
                            org.osmdroid.util.GeoPoint(
                                trackPoint.latitude,
                                trackPoint.longitude
                            )
                        )
                    }
                }
            }
        } ?: {

        }
    } catch (e: IOException) {
        // do something with this exception
        e.printStackTrace()
    } catch (e: XmlPullParserException) {
        // do something with this exception
        e.printStackTrace()
    }

    return geoPoints
}

//Ksizámolja a távolságot egy GeoPoint lista alapján
fun getDistanceFromGeoPoints(geoPoints: List<GeoPoint>): Double {
    var distance = 0.0
    var geoPoint1 = geoPoints[0]
    var geoPoint2: GeoPoint
    for (geoPoint in geoPoints) {
        geoPoint2 = geoPoint
        distance += getDistanceFromLatLonInKm(geoPoint1.latitude, geoPoint1.longitude, geoPoint2.latitude, geoPoint2.longitude)
        geoPoint1 = geoPoint
    }
    return distance
}

//Kiszámolja az átlagsebességet
fun calculateAverageSpeedInKmH(
    startTime: Long, // e.g. milliseconds since epoch
    endTime: Long,
    distanceInKm: Double
): Double {
    val timeInHours = (endTime - startTime).toDouble() / (60 * 60)
    return if (timeInHours > 0) distanceInKm / timeInHours else 0.0
}
