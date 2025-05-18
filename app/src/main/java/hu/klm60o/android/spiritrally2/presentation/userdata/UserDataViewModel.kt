package hu.klm60o.android.spiritrally2.presentation.userdata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.klm60o.android.spiritrally2.domain.model.Response
import hu.klm60o.android.spiritrally2.domain.model.UserData
import hu.klm60o.android.spiritrally2.domain.repository.AddUserDataResponse
import hu.klm60o.android.spiritrally2.domain.repository.EditUserDataResponse
import hu.klm60o.android.spiritrally2.domain.repository.UserDataListResponse
import hu.klm60o.android.spiritrally2.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val repo: UserDataRepository
): ViewModel() {
    private val _userDataListState = MutableStateFlow<UserDataListResponse>(Response.Loading)
    val userDataListState: StateFlow<UserDataListResponse> = _userDataListState.asStateFlow()

    private val _addUserDataState = MutableStateFlow<AddUserDataResponse>(Response.Idle)
    val addUserDataState: StateFlow<AddUserDataResponse> = _addUserDataState.asStateFlow()

    private val _editUserDataState = MutableStateFlow<EditUserDataResponse>(Response.Idle)
    val editUserDataState: StateFlow<EditUserDataResponse> = _editUserDataState.asStateFlow()

    init {
        getUserDataList()
    }

    private fun getUserDataList() = viewModelScope.launch {
        repo.getUserDataFromFirestore().collect { response ->
            _userDataListState.value = response
        }
    }

    fun addUserData(userData: UserData) = viewModelScope.launch {
        _addUserDataState.value = Response.Loading
        _addUserDataState.value = repo.addUserData(userData)
    }

    fun resetAddUserDataState() {
        _addUserDataState.value = Response.Idle
    }

    fun editUserData(userDataUpdates: Map<String, String>) = viewModelScope.launch {
        _editUserDataState.value = Response.Loading
        _editUserDataState.value = repo.editUserData(userDataUpdates)
    }

    fun resetEditUserDataState() {
        _addUserDataState.value = Response.Idle
    }
}