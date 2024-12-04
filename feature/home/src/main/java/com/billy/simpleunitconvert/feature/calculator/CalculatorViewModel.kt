package com.billy.simpleunitconvert.feature.calculator

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.billy.simpleunitconvert.core.data.repository.query.QueryDataBaseRepository
import com.billy.simpleunitconvert.core.model.calculator.BackResult
import com.billy.simpleunitconvert.core.model.calculator.UnitCategory
import com.billy.simpleunitconvert.core.model.home.UnitItemData
import com.billy.simpleunitconvert.core.viewmodel.BaseViewModel
import com.billy.simpleunitconvert.core.viewmodel.StateExtensions.updateState
import com.billy.simpleunitconvert.core.viewmodel.UiState
import com.billy.simpleunitconvert.core.viewmodel.ViewModelStateFlow
import com.billy.simpleunitconvert.feature.common.takeEnoughLength
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
    private var categorySave: UnitCategory? = null
    private var isClickUnitInput: Boolean? = null

    init {
         viewModelScope.launch {
             savedStateHandle.getStateFlow<UnitCategory?>("unitCategory", null)
                 .collect { unitCategory ->
                     unitCategory?.let { category ->
                         categorySave = category
                         uiState.updateBatchData(
                             category = category.category
                         )
                         queryDataBaseRepository.queryUnitByCategory(category.category).collect {
                             currentListUnit = it.toMutableList()
                             initDataUnit()
                         }
                     }

                }
         }

        viewModelScope.launch {
            categorySave?.category?.let {
                queryDataBaseRepository.getUnitConvert(it).collect { isFavorite ->
                    uiState.updateBatchData(
                        isFavorite = isFavorite.isFavorite
                    )
                }

            }
        }
    }

    private fun initDataUnit() {
        if (currentListUnit.isEmpty()) {
            uiState.updateState(
                error = "There is something wrong, please try again",
                loading = false
            )
            return
        }

        if (unitInput == null) unitInput = if (categorySave?.unitName != null) currentListUnit.find { it.name == categorySave?.unitName } else currentListUnit.getOrNull(0)
        if (unitResult == null) unitResult = currentListUnit.getOrNull(1) ?: unitInput
        if (unitInput == null || unitResult ==  null) {
           uiState.updateState(error = "There is something wrong, please try again", loading = false)
            return
        }

        uiState.updateBatchData(
            updateUnitResult = { copy(name = unitResult!!.name, symbol = unitResult!!.symbol)},
            updateUnitInput = { copy(name = unitInput!!.name, symbol = unitInput!!.symbol)},
        )
    }


    private fun handleButtonClick(newNumber: String): String? {
        val input = uiState.value.data.calculatorDisplay.input
        return when (newNumber) {
            "." -> {
                if (input.contains(".")) {
                    return null
                }
                if (input.isEmpty()) {
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
               viewModelScope.launch {
                   categorySave?.category?.let {
                       queryDataBaseRepository.updateFavoriteUnit(categorySave?.category ?: "", !uiState.value.data.isFavorite).collect {
                           uiState.updateBatchData(
                               isFavorite = !uiState.value.data.isFavorite
                           )
                       }
                   }
               }
            }

            is CalculatorEvent.OnClickButton -> {
                handleButtonClick(event.value)?.let { resultForInPut ->
                   val result = calculatorResult(resultForInPut)
                    uiState.updateBatchData(
                        updateUnitCalculatorDisplay = {
                            setValidData(input = resultForInPut,result = result)
                        }
                    )
                }
            }

            is CalculatorEvent.OnClickSwap -> {
                doSwapUnit(uiState.value.data.calculatorDisplay.input)
            }

            is CalculatorEvent.OnClickOpenSearch ->{
                isClickUnitInput = event.isClickUnitInput
            }

            is CalculatorEvent.OnItemResult -> {
                if (isClickUnitInput == true) {
                    unitInput = currentListUnit.find { it.name == event.itemResult.itemSelected }
                } else {
                    unitResult = currentListUnit.find { it.name == event.itemResult.itemSelected }
                }
                recalculate(uiState.value.data.calculatorDisplay.input)
            }
        }
    }

    private fun calculatorResult(value: String): String {
        if (value.isEmpty()) {
            return ""
        }
        if (unitInput?.conversionFactor !=null && unitResult?.conversionFactor != null) {
           val baseValue = value.toDouble() * unitInput!!.conversionFactor!!
            return (baseValue / unitResult!!.conversionFactor!!).toString()
        }

        if (unitInput?.scaleFactor !=null && unitInput?.offset !=null && unitResult?.scaleFactor != null && unitResult?.offset != null) {
            val kelvin = (value.toDouble() * unitInput!!.scaleFactor!!) + unitInput!!.offset!!
            return ((kelvin - unitResult!!.offset!!) / unitResult!!.scaleFactor!!).toString()
        }
        return "Has error when calculate, Please close and open the app again"
    }

    private fun recalculate(value: String) {
       val result = calculatorResult(value)
        uiState.updateBatchData(
            updateUnitResult = { copy(name = unitResult!!.name, symbol = unitResult!!.symbol)},
            updateUnitInput = { copy(name = unitInput!!.name, symbol = unitInput!!.symbol)},
            updateUnitCalculatorDisplay = {copy(input = value, result = result)}
        )
    }

    private fun doSwapUnit(value: String) {
        val temp = unitInput
        unitInput = unitResult
        unitResult = temp
        recalculate(value)
    }

}

sealed interface CalculatorEvent {
    data class OnClickButton(val value: String) : CalculatorEvent

    data class OnClickOpenSearch(val isClickUnitInput: Boolean) : CalculatorEvent

    data class OnItemResult(val itemResult: BackResult) : CalculatorEvent

    data object OnClickFavorite : CalculatorEvent

    data object OnClickSwap : CalculatorEvent

}

@Stable
data class CalculatorState(
    val unitInput: UnitInputState = UnitInputState(),
    val unitResult: UnitResultState = UnitResultState(),
    val calculatorDisplay: CalculatorDisplayState = CalculatorDisplayState(),
    val isFavorite: Boolean = false,
    val category: String = "",
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
) {
   fun setValidData(input: String, result: String): CalculatorDisplayState {
       return copy(input = input.takeEnoughLength(), result = result.takeEnoughLength())
   }
}


