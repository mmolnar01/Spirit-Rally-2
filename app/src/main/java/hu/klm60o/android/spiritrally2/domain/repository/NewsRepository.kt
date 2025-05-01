package hu.klm60o.android.spiritrally2.domain.repository

import hu.klm60o.android.spiritrally2.domain.model.News
import hu.klm60o.android.spiritrally2.domain.model.Response
import kotlinx.coroutines.flow.Flow

typealias NewsResponse = Response<List<News>>

interface NewsRepository {
    fun getNewsFromFirestore(): Flow<NewsResponse>
}