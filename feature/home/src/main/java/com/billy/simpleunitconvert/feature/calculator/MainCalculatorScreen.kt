package com.billy.simpleunitconvert.feature.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.colors
import com.billy.simpleunitconvert.core.model.calculator.BackResult
import com.billy.simpleunitconvert.feature.common.Utils

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

    LaunchedEffect(true) {
        Utils.isJustShowOpenApp = false
        Utils.incrementCount()
    }

    Scaffold(
        containerColor = colors.primary,
        topBar = {
            AppBarCalculatorScreen(
                onClickFavorite = {
                    viewModel.onEvent(CalculatorEvent.OnClickFavorite)
                },
                isFavorite = uiState.data.isFavorite,
            )

        }
    ) {  innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {

            SectionCalculatorContent(
                calculatorState = uiState.data,
                onEvent = viewModel::onEvent
            )
        }

    }
}

@Composable
@Preview
fun MainCalculatorScreenPreview() {
    AppUnitTheme {
       CalculatorScreen()
   }
}
