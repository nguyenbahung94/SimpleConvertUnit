package com.billy.simpleunitconvert.feature.calculator

import androidx.compose.runtime.Stable
import com.billy.simpleunitconvert.core.data.repository.query.QueryDataBaseRepository
import com.billy.simpleunitconvert.core.viewmodel.BaseViewModel
import com.billy.simpleunitconvert.core.viewmodel.ViewModelStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val queryDataBaseRepository: QueryDataBaseRepository
): BaseViewModel() {
    internal  val uiState: ViewModelStateFlow<CalculatorState?> =  viewModelStateFlow(null)

    fun handleButtonClick() {
//       val input = uiState.value?.input ?: ""
//        when (value) {
//            "." -> if (!_input.value.contains(".")) _input.value += value
//            "Del" -> if (_input.value.isNotEmpty()) _input.value = _input.value.dropLast(1)
//            "AC" -> _input.value = ""
//            else -> _input.value += value
//        }
    }

    fun clickOnFavorite() {

    }

}


@Stable
sealed interface CalculatorUiState {

    data object Idle : CalculatorUiState

    data object Loading : CalculatorUiState

    data class Error(val message: String?) : CalculatorUiState
}

data class CalculatorState(
    val input: String,
    val result: String,
    val isFavorite: Boolean,
    val nameUnitInput: String,
    val symbolUnitInput: String,
    val nameUnitResult: String,
    val symbolUnitResult: String,
    val uiState: CalculatorUiState = CalculatorUiState.Loading
)
