package com.billy.simpleunitconvert.feature.search

import android.util.Log
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
import kotlinx.coroutines.flow.combine
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

    private val _nameChecked = MutableStateFlow(true)
    private val nameChecked: StateFlow<Boolean> = _nameChecked

    private val _symbolChecked = MutableStateFlow(true)
    private val symbolChecked: StateFlow<Boolean> = _symbolChecked

    private val _categoryChecked = MutableStateFlow(true)
    private val categoryChecked: StateFlow<Boolean> = _categoryChecked

    private val _searchCategory = MutableStateFlow<SearchCategory?>(null)
    val searchCategory: StateFlow<SearchCategory?> = _searchCategory

    init {
        viewModelScope.launch {
            savedStateHandle.getStateFlow<SearchCategory?>("searchCategory", null)
                .collect { valueSearchCategory ->
                    valueSearchCategory?.let {
                        _searchCategory.value = it
                        _searchQuery.update { "" }
                    }

                }
        }
    }

    @OptIn(FlowPreview::class)
    val searchResults: StateFlow<PagingData<UnitItemData>> = combine(
        searchQuery.debounce(500),
        nameChecked,
        symbolChecked,
        categoryChecked
    ) { query, nameChecked, symbolChecked, categoryChecked ->
        SearchParams(query, nameChecked, symbolChecked, categoryChecked)
    }.flatMapLatest { params ->
        queryDataBaseRepository.queryUnitByKeWord(
            keyWord = params.query,
            category = searchCategory.value?.category,
            includeName = params.nameChecked,
            includeSymbol = params.symbolChecked,
            includeCategory = params.categoryChecked
        ).onStart {
            uiState.value = uiState.value.copy(isLoading = true)
        }.map { pagingData ->
            Log.e("HungLog", "pagingData: new data")
            uiState.value = uiState.value.copy(isLoading = false)
            pagingData.filter { item -> item.name != searchCategory.value?.nameIgnore }
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
            is SearchEvent.ChangeSearchSetting -> {
                val listSettings = event.searchSettings
                _nameChecked.value = listSettings.contains(SearchSetting.NAME)
                _symbolChecked.value = listSettings.contains(SearchSetting.SYMBOL)
                _categoryChecked.value = listSettings.contains(SearchSetting.CATEGORY)
            }
        }
    }

}


sealed interface SearchEvent {
    data class OnSearchQueryChange(val query: String) : SearchEvent
    data class ChangeSearchSetting(val searchSettings: List<SearchSetting>) : SearchEvent
}


enum class SearchSetting {
    ALL, NAME, SYMBOL, CATEGORY
}

@Stable
data class SearchState(
    val search: String,
)

data class SearchParams(
    val query: String,
    val nameChecked: Boolean,
    val symbolChecked: Boolean,
    val categoryChecked: Boolean
)
