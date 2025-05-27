package hu.klm60o.android.spiritrally2.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import hu.klm60o.android.spiritrally2.domain.model.Racepoint
import hu.klm60o.android.spiritrally2.domain.model.Response.Failure
import hu.klm60o.android.spiritrally2.domain.model.Response.Success
import hu.klm60o.android.spiritrally2.domain.repository.EditRacepointResponse
import hu.klm60o.android.spiritrally2.domain.repository.RacepointRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class RacepointsRepositoryImpl (
    private val racepointsRef: CollectionReference,
    private val commonRef: CollectionReference
): RacepointRepository {

    override fun getRacepointsFromFirestore() = callbackFlow {
        val snapshotListener = racepointsRef.addSnapshotListener { snapshot, e ->
            if (snapshot != null && !snapshot.isEmpty) {
                val racepoints = snapshot.toObjects(Racepoint::class.java)
                trySend(Success(racepoints))
            } else {
                //Ha még nincs a user-nek saját kollekciója, lekérdezzük a verseny adatait a közösből
                commonRef.get()
                    .addOnSuccessListener { fallbackSnapshot ->
                        val fallbackRacepoints = fallbackSnapshot.toObjects(Racepoint::class.java)
                        val batch = FirebaseFirestore.getInstance().batch()

                        fallbackSnapshot.documents.forEach { doc ->
                            val newDocRef = racepointsRef.document(doc.id)
                            batch.set(newDocRef, doc.data!!)
                        }

                        batch.commit()
                            .addOnSuccessListener {
                                trySend(Success(fallbackRacepoints))
                            }
                            .addOnFailureListener { batchError ->
                                trySend(Failure(batchError))
                            }
                    }
                    .addOnFailureListener { fetchError ->
                        trySend(Failure(fetchError))
                    }
            }
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun editRacepointInFirestore(
        racepointId: String,
        timestamp: Timestamp
    ): EditRacepointResponse {
        return try {
            racepointsRef.document(racepointId).update("timestamp", timestamp).await()
            Success(Unit)
        } catch (e: Exception) {
            Failure(e)
        }
    }
}
