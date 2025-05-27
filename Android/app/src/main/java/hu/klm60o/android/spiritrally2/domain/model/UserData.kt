package hu.klm60o.android.spiritrally2.domain.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

//Categories:
//1. Autó
//2. Motor
//3. ATV
//4. Minimál
data class UserData(
    var id: String? = null,
    var number: Int? = null,
    var category: Int? = null,
    var location: GeoPoint? = null,
    var timestamp: Timestamp? = null
)