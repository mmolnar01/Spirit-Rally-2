package hu.klm60o.android.spiritrally2.domain.repository

import hu.klm60o.android.spiritrally2.domain.model.Response
import hu.klm60o.android.spiritrally2.domain.model.UserData
import kotlinx.coroutines.flow.Flow

typealias UserDataListResponse = Response<List<UserData>>
typealias AddUserDataResponse = Response<Unit>
typealias EditUserDataResponse = Response<Unit>

interface UserDataRepository {
    fun getUserDataFromFirestore(): Flow<UserDataListResponse>

    suspend fun addUserData(userData: Map<String, String>): AddUserDataResponse

    suspend fun editUserData(userDataUpdates: Map<String, String>): EditUserDataResponse
}