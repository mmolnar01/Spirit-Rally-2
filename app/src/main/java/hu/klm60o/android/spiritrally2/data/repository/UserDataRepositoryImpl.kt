package hu.klm60o.android.spiritrally2.data.repository

import com.google.firebase.firestore.CollectionReference
import hu.klm60o.android.spiritrally2.domain.model.News
import hu.klm60o.android.spiritrally2.domain.model.Response
import hu.klm60o.android.spiritrally2.domain.model.Response.Failure
import hu.klm60o.android.spiritrally2.domain.model.Response.Success
import hu.klm60o.android.spiritrally2.domain.model.UserData
import hu.klm60o.android.spiritrally2.domain.repository.AddUserDataResponse
import hu.klm60o.android.spiritrally2.domain.repository.EditUserDataResponse
import hu.klm60o.android.spiritrally2.domain.repository.UserDataListResponse
import hu.klm60o.android.spiritrally2.domain.repository.UserDataRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UserDataRepositoryImpl(
    private val userDataRef: CollectionReference
): UserDataRepository {
    override fun getUserDataFromFirestore() = callbackFlow {
        val snapshotListener = userDataRef.addSnapshotListener { snapshot, e ->
            if (snapshot != null && !snapshot.isEmpty) {
                val userData = snapshot.toObjects(UserData::class.java)
                trySend(Success(userData))
            } else {
                trySend(Failure(e))
            }
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addUserData(userData: Map<String, String>) = try {
        userDataRef.add(userData).await()
        Response.Success(Unit)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun editUserData(userDataUpdates: Map<String, String>) = try {
        val userDataId = userDataUpdates.getValue("id")
        userDataRef.document(userDataId).update(userDataUpdates).await()
        Response.Success(Unit)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}