package com.billy.simpleunitconvert.feature.common

import com.billy.simpleunitconvert.core.viewmodel.UiState
import com.billy.simpleunitconvert.core.viewmodel.ViewModelStateFlow
import com.billy.simpleunitconvert.feature.calculator.CalculatorDisplayState
import com.billy.simpleunitconvert.feature.calculator.CalculatorState
import com.billy.simpleunitconvert.feature.calculator.UnitInputState
import com.billy.simpleunitconvert.feature.calculator.UnitResultState
import kotlinx.coroutines.flow.MutableStateFlow


fun String.takeEnoughLength(): String {
   return this.take(Setting.MAX_NUMBER_LENGTH)
}

private inline fun <T> MutableStateFlow<T>.update(updateFunction: (T) -> T) { this.value = updateFunction(this.value) }

fun ViewModelStateFlow<UiState<CalculatorState>>.updateBatchData(
   updateUnitInput: (UnitInputState.() -> UnitInputState)? = null,
   updateUnitResult: (UnitResultState.() -> UnitResultState)? = null,
   updateUnitCalculatorDisplay: (CalculatorDisplayState.() -> CalculatorDisplayState)? = null
) {
    update { state ->
      state.copy(
          data = state.data.copy(
              unitInput = updateUnitInput?.invoke(state.data.unitInput) ?: state.data.unitInput,
              unitResult = updateUnitResult?.invoke(state.data.unitResult) ?: state.data.unitResult,
              calculatorDisplay = updateUnitCalculatorDisplay?.invoke(state.data.calculatorDisplay) ?: state.data.calculatorDisplay
          )
      )
   }
}

