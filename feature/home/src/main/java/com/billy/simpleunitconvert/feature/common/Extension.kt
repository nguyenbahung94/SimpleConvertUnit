package com.billy.simpleunitconvert.feature.common

import androidx.compose.runtime.compositionLocalOf
import com.billy.simpleunitconvert.core.model.search.SearchCategory
import com.billy.simpleunitconvert.core.viewmodel.UiState
import com.billy.simpleunitconvert.core.viewmodel.ViewModelStateFlow
import com.billy.simpleunitconvert.feature.calculator.CalculatorDisplayState
import com.billy.simpleunitconvert.feature.calculator.CalculatorState
import com.billy.simpleunitconvert.feature.calculator.UnitInputState
import com.billy.simpleunitconvert.feature.calculator.UnitResultState
import kotlinx.coroutines.flow.MutableStateFlow

// list of constants compositionLocalOf
val LocalCategoryProvider = compositionLocalOf<SearchCategory?> { null }


fun String.takeEnoughLength(): String {
   return this.take(Setting.MAX_NUMBER_LENGTH)
}

private inline fun <T> MutableStateFlow<T>.update(updateFunction: (T) -> T) { this.value = updateFunction(this.value) }

fun ViewModelStateFlow<UiState<CalculatorState>>.updateBatchData(
   updateUnitInput: (UnitInputState.() -> UnitInputState)? = null,
   updateUnitResult: (UnitResultState.() -> UnitResultState)? = null,
   updateUnitCalculatorDisplay: (CalculatorDisplayState.() -> CalculatorDisplayState)? = null,
    category: String? = null,
    isFavorite: Boolean? = null
) {
    update { state ->
      state.copy(
          data = state.data.copy(
              unitInput = updateUnitInput?.invoke(state.data.unitInput) ?: state.data.unitInput,
              unitResult = updateUnitResult?.invoke(state.data.unitResult) ?: state.data.unitResult,
              calculatorDisplay = updateUnitCalculatorDisplay?.invoke(state.data.calculatorDisplay) ?: state.data.calculatorDisplay,
              category = category ?: state.data.category,
              isFavorite = isFavorite ?: state.data.isFavorite
          )
      )
   }
}

fun String.capitalizeFirstChar(): String {
   return replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}
