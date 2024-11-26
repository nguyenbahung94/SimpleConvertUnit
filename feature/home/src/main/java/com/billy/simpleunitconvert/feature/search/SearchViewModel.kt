package com.billy.simpleunitconvert.feature.search

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.billy.simpleunitconvert.core.data.repository.query.QueryDataBaseRepository
import com.billy.simpleunitconvert.core.model.home.UnitItemData
import com.billy.simpleunitconvert.core.model.search.SearchCategory
import com.billy.simpleunitconvert.core.viewmodel.BaseViewModel
import com.billy.simpleunitconvert.core.viewmodel.StateExtensions.update
import com.billy.simpleunitconvert.core.viewmodel.UiState
import com.billy.simpleunitconvert.core.viewmodel.ViewModelStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val queryDataBaseRepository: QueryDataBaseRepository,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    internal val uiState: ViewModelStateFlow<UiState<SearchState>> =
        viewModelStateFlow(UiState(isLoading = true, data = SearchState("")))

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery
    var searchCategory: SearchCategory? = null
        private set

    init {
        viewModelScope.launch {
            savedStateHandle.getStateFlow<SearchCategory?>("searchCategory", null)
                .collect { valueSearchCategory ->
                    valueSearchCategory?.let {
                        searchCategory = it
                        _searchQuery.update { "" }
                    }

                }
        }
    }

    @OptIn(FlowPreview::class)
    val searchResults: StateFlow<PagingData<UnitItemData>> =
        searchQuery.debounce(400).flatMapLatest { query ->
                queryDataBaseRepository.queryUnitByKeWord(query, searchCategory?.category)
                    .onStart { uiState.value = uiState.value.copy(isLoading = true) }
                    .map {
                        uiState.value = uiState.value.copy(isLoading = false)
                        it.filter { item -> item.name != searchCategory?.nameIgnore }
                    }.catch { e ->
                        uiState.value = uiState.value.copy(isLoading = false, error = e.message)
                        emit(PagingData.empty())
                    }
            }.cachedIn(viewModelScope).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = PagingData.empty()
            )


    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSearchQueryChange -> onSearchQueryChange(event.query)
        }
    }

}


sealed interface SearchEvent {
    data class OnSearchQueryChange(val query: String) : SearchEvent
}

@Stable
data class SearchState(
    val search: String,
)
