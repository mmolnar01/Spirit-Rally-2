package hu.klm60o.android.spiritrally2.presentation.racepoints

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.cache.LoadingCache
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.google.firebase.firestore.GeoPoint
import hu.klm60o.android.spiritrally2.domain.model.Racepoint
import hu.klm60o.android.spiritrally2.domain.model.Response
import hu.klm60o.android.spiritrally2.domain.repository.EditRacepointResponse
import hu.klm60o.android.spiritrally2.domain.repository.RacepointRepository
import hu.klm60o.android.spiritrally2.domain.repository.RacepointsResponse
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    fun editRacepoint(racepointId: Int, geoPoint: GeoPoint, timestamp: Timestamp) = viewModelScope.launch {
        _editRacepointState.value = Response.Loading
        _editRacepointState.value = repo.editRacepointInFirestore(racepointId, geoPoint, timestamp)
    }

    fun resetEditRacepointState() {
        _editRacepointState.value = Response.Idle
    }
}