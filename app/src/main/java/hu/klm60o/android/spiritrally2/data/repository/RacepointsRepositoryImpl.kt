package hu.klm60o.android.spiritrally2.data.repository

import com.google.firebase.firestore.CollectionReference
import hu.klm60o.android.spiritrally2.domain.model.Racepoint
import hu.klm60o.android.spiritrally2.domain.model.Response
import hu.klm60o.android.spiritrally2.domain.model.Response.Failure
import hu.klm60o.android.spiritrally2.domain.model.Response.Success
import hu.klm60o.android.spiritrally2.domain.repository.EditRacepointResponse
import hu.klm60o.android.spiritrally2.domain.repository.RacepointRepository
import hu.klm60o.android.spiritrally2.domain.repository.RacepointsResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.Timestamp
import kotlinx.coroutines.tasks.await

@Singleton
class RacepointsRepositoryImpl (
    private val racepointsRef: CollectionReference
): RacepointRepository {

    override fun getRacepointsFromFirestore() = callbackFlow {
        val snapshotListener = racepointsRef.addSnapshotListener { snapshot, e ->
            val racepointsResponse = if (snapshot != null) {
                val racepoints = snapshot.toObjects(Racepoint::class.java)
                Success(racepoints)
            } else {
                Failure(e)
            }
            trySend(racepointsResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun editRacepointInFirestore(
        racepointId: Int,
        geoPoint: GeoPoint,
        timestamp: Timestamp
    ): EditRacepointResponse {
        return try {
            val racepoint = Racepoint(
                id = racepointId,
                location = geoPoint,
                timestamp = timestamp
            )
            racepointsRef.document(racepointId.toString()).set(racepoint).await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }
}
