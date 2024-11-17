package com.billy.simpleunitconvert.feature.calculator

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.billy.simpleunitconvert.core.data.repository.query.QueryDataBaseRepository
import com.billy.simpleunitconvert.core.model.calculator.UnitCategory
import com.billy.simpleunitconvert.core.model.home.UnitItemData
import com.billy.simpleunitconvert.core.viewmodel.BaseViewModel
import com.billy.simpleunitconvert.core.viewmodel.StateExtensions.updateState
import com.billy.simpleunitconvert.core.viewmodel.UiState
import com.billy.simpleunitconvert.core.viewmodel.ViewModelStateFlow
import com.billy.simpleunitconvert.feature.common.updateBatchData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val queryDataBaseRepository: QueryDataBaseRepository,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    internal val uiState: ViewModelStateFlow<UiState<CalculatorState>> = viewModelStateFlow(
        UiState(isLoading = true, data = CalculatorState())
    )
    private var currentListUnit = mutableListOf<UnitItemData>()
    private var unitInput: UnitItemData? = null
    private var unitResult: UnitItemData? = null

    init {

         viewModelScope.launch {
             savedStateHandle.getStateFlow<UnitCategory?>("unitCategory", null).collect { unitCategory ->
                    unitCategory?.let {category ->
                        queryDataBaseRepository.queryUnitByCategory(category.category).collect {
                            currentListUnit = it.toMutableList()
                            initDataUnit()
                        }
                    }
                }
         }
    }

    private fun initDataUnit() {
         unitInput = currentListUnit.getOrNull(0)
         unitResult = currentListUnit.getOrNull(1) ?: unitInput

        if (unitInput == null || unitResult ==  null) {
           uiState.updateState(error = "There is something wrong, please try again", loading = false)
            return
        }
        Log.e("CalculatorViewModel", "unitInput: $unitInput")
        Log.e("CalculatorViewModel", "unitResult: $unitResult")
        uiState.updateBatchData(
            updateUnitResult = { copy(name = unitInput!!.name, symbol = unitInput!!.symbol)},
            updateUnitInput = { copy(name = unitResult!!.name, symbol = unitResult!!.symbol)}
        )
    }


    private fun handleButtonClick(newNumber: String): String? {
        val input = uiState.value.data.calculatorDisplay.input
        return when (newNumber) {
            "." -> {
                if (!input.contains(".")) {
                    return null
                }
                input + newNumber
            }

            "Del" -> {
                if (input.isEmpty()) {
                    return null

                }
                input.dropLast(1)
            }

            "AC" -> ""

            else -> input + newNumber
        }
    }

    fun onEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.OnClickFavorite -> {

            }

            is CalculatorEvent.OnClickButton -> {
                handleButtonClick(event.value)?.let { resultForInPut ->

                }
            }
        }
    }

    private fun calculatorResult(input: String): String {
        return input + "a"
    }

}

sealed interface CalculatorEvent {
    data class OnClickButton(val value: String) : CalculatorEvent

    data object OnClickFavorite : CalculatorEvent
}

@Stable
data class CalculatorState(
    val unitInput: UnitInputState = UnitInputState(),
    val unitResult: UnitResultState = UnitResultState(),
    val calculatorDisplay: CalculatorDisplayState = CalculatorDisplayState(),
    val isFavorite: Boolean = false,
)

@Stable
data class UnitInputState(
    val name: String = "",
    val symbol: String = "",
)

@Stable
data class UnitResultState(
    val name: String = "",
    val symbol: String = "",
)

@Stable
data class CalculatorDisplayState(
    val input: String = "",
    val result: String = "",
    val isFavorite: Boolean = false,
)


