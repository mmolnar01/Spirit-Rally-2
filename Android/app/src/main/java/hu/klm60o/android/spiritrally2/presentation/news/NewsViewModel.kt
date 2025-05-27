package hu.klm60o.android.spiritrally2.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.klm60o.android.spiritrally2.domain.model.Response
import hu.klm60o.android.spiritrally2.domain.repository.NewsRepository
import hu.klm60o.android.spiritrally2.domain.repository.NewsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel(){
    private val _newsState = MutableStateFlow<NewsResponse>(Response.Loading)
    val newsState: StateFlow<NewsResponse> = _newsState.asStateFlow()

    init {
        getNewsList()
    }

    private fun getNewsList() = viewModelScope.launch {
        repository.getNewsFromFirestore().collect { response ->
            _newsState.value = response
        }
    }
}