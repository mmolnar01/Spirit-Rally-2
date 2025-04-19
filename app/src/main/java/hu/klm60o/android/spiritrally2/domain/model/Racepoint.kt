package hu.klm60o.android.spiritrally2.domain.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class Racepoint(
    var id: Int? = null,
    var location: GeoPoint? = null,
    var timestamp: Timestamp? = null
)