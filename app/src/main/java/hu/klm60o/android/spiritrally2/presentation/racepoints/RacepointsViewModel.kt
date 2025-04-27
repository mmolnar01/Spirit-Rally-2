package hu.klm60o.android.spiritrally2.presentation.racepoints

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.klm60o.android.spiritrally2.domain.model.Response
import hu.klm60o.android.spiritrally2.domain.repository.EditRacepointResponse
import hu.klm60o.android.spiritrally2.domain.repository.RacepointRepository
import hu.klm60o.android.spiritrally2.domain.repository.RacepointsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RacepointsViewModel @Inject constructor(
    private val repo: RacepointRepository
): ViewModel() {
    private val _racepointsState = MutableStateFlow<RacepointsResponse>(Response.Loading)
    val racepointsState: StateFlow<RacepointsResponse> = _racepointsState.asStateFlow()

    private val _editRacepointState = MutableStateFlow<EditRacepointResponse>(Response.Idle)
    val editRacepointState: StateFlow<EditRacepointResponse> = _editRacepointState.asStateFlow()

    init {
        getRacepointList()
    }

    private fun getRacepointList() = viewModelScope.launch {
        repo.getRacepointsFromFirestore().collect { response ->
            _racepointsState.value = response
        }
    }

    fun editRacepoint(racepointId: String, timestamp: Timestamp) = viewModelScope.launch {
        _editRacepointState.value = Response.Loading
        _editRacepointState.value = repo.editRacepointInFirestore(racepointId, timestamp)
    }

    fun resetEditRacepointState() {
        _editRacepointState.value = Response.Idle
    }
}