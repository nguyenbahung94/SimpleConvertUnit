package com.billy.simpleunitconvert.feature.home

import androidx.compose.runtime.Stable
import com.billy.simpleunitconvert.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

): BaseViewModel() {
    internal val uiState = viewModelStateFlow<HomeUiState>(HomeUiState.Loading)
}



@Stable
internal sealed interface HomeUiState {

    data object Idle : HomeUiState

    data object Loading : HomeUiState

    data class Error(val message: String?) : HomeUiState
}
