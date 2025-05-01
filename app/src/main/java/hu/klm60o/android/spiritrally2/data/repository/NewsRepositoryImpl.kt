package hu.klm60o.android.spiritrally2.data.repository

import com.google.firebase.firestore.CollectionReference
import hu.klm60o.android.spiritrally2.domain.model.News
import hu.klm60o.android.spiritrally2.domain.model.Response.Failure
import hu.klm60o.android.spiritrally2.domain.model.Response.Success
import hu.klm60o.android.spiritrally2.domain.repository.NewsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl(
    private val newsRef: CollectionReference
): NewsRepository {
    override fun getNewsFromFirestore() = callbackFlow {
        val snapshotListener = newsRef.addSnapshotListener { snapshot, e ->
            if (snapshot != null && !snapshot.isEmpty) {
                val news = snapshot.toObjects(News::class.java)
                trySend(Success(news))
            } else {
                trySend(Failure(e))
            }
        }

        awaitClose {
            snapshotListener.remove()
        }
    }
}