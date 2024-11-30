package com.billy.simpleunitconvert.feature.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.model.calculator.BackResult

@Composable
fun CalculatorScreen(
    itemResult: BackResult? = null,
    viewModel: CalculatorViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(itemResult?.itemSelected) {
        itemResult?.let {
            viewModel.onEvent(CalculatorEvent.OnItemResult(it))
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(WindowInsets.statusBars.asPaddingValues())
    ) {

        AppBarCalculatorScreen(
            onClickFavorite = {
                viewModel.onEvent(CalculatorEvent.OnClickFavorite)
            },
            isFavorite = uiState.data.isFavorite,
        )

        SectionCalculatorContent(
            calculatorState = uiState.data,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
@Preview
fun MainCalculatorScreenPreview() {
    AppUnitTheme {
       CalculatorScreen()
   }
}
