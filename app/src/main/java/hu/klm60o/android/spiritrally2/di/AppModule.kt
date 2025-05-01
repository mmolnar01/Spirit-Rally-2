package hu.klm60o.android.spiritrally2.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.klm60o.android.spiritrally2.data.repository.NewsRepositoryImpl
import hu.klm60o.android.spiritrally2.domain.repository.RacepointRepository
import hu.klm60o.android.spiritrally2.data.repository.RacepointsRepositoryImpl
import hu.klm60o.android.spiritrally2.domain.repository.NewsRepository


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideRacepointsRespository(
        db: FirebaseFirestore
    ): RacepointRepository = RacepointsRepositoryImpl(
        racepointsRef = db.collection("results").document(Firebase.auth.uid.toString()).collection("my_results"),
        commonRef = db.collection("data")
    )

    @Provides
    fun provideNewsRepository(
        db: FirebaseFirestore
    ): NewsRepository = NewsRepositoryImpl(
        newsRef = db.collection("news")
    )
}