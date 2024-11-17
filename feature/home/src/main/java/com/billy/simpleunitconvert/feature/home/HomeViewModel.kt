package com.billy.simpleunitconvert.feature.home

import androidx.lifecycle.viewModelScope
import com.billy.simpleunitconvert.core.data.repository.query.QueryDataBaseRepository
import com.billy.simpleunitconvert.core.model.home.HomeUnit
import com.billy.simpleunitconvert.core.viewmodel.BaseViewModel
import com.billy.simpleunitconvert.core.viewmodel.UiState
import com.billy.simpleunitconvert.core.viewmodel.ViewModelStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val queryDataBaseRepository: QueryDataBaseRepository
): BaseViewModel() {
    internal val uiState: ViewModelStateFlow<UiState<HomeState>> =  viewModelStateFlow(UiState(isLoading = true, data = HomeState("Home")))

    val homeUnit: StateFlow<List<HomeUnit>> = queryDataBaseRepository.queryHomeUnits()
        .onStart {
            uiState.value = uiState.value.copy(isLoading = true)
        }
        .onCompletion { uiState.value = uiState.value.copy(isLoading = false) }
        .catch { e ->
            uiState.value = uiState.value.copy(isLoading = false, error = e.message)
            emit(emptyList())
        }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

}

data class HomeState(
    val homeState: String
)



