package hu.klm60o.android.spiritrally2.domain.repository

import com.google.firebase.Timestamp
import hu.klm60o.android.spiritrally2.domain.model.Racepoint
import hu.klm60o.android.spiritrally2.domain.model.Response
import kotlinx.coroutines.flow.Flow

typealias RacepointsResponse = Response<List<Racepoint>>
typealias EditRacepointResponse = Response<Unit>

interface RacepointRepository {
    fun getRacepointsFromFirestore(): Flow<RacepointsResponse>

    suspend fun editRacepointInFirestore(racepointId: String, timestamp: Timestamp): EditRacepointResponse
}
