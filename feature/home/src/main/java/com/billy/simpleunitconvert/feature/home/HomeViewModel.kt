package com.billy.simpleunitconvert.feature.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import com.billy.simpleunitconvert.core.data.repository.query.QueryDataBaseRepository
import com.billy.simpleunitconvert.core.model.HomeUnit
import com.billy.simpleunitconvert.core.viewmodel.BaseViewModel
import com.billy.simpleunitconvert.core.viewmodel.RestrictedApi
import com.billy.simpleunitconvert.core.viewmodel.ViewModelStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val queryDataBaseRepository: QueryDataBaseRepository
): BaseViewModel() {
    internal val uiState: ViewModelStateFlow<HomeUiState> =  viewModelStateFlow(HomeUiState.Loading)

    val homeUnit: StateFlow<List<HomeUnit>> = queryDataBaseRepository.queryHomeUnits()
        .catch { e ->
            uiState.value = HomeUiState.Error(e.message)
            emit(emptyList())
        }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

}

@Stable
internal sealed interface HomeUiState {

    data object Idle : HomeUiState

    data object Loading : HomeUiState

    data class Error(val message: String?) : HomeUiState
}



