package com.billy.simpleunitconvert.feature.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
 //   val input by viewModel.input.collectAsStateWithLifecycle()
//    val result by viewModel.result.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        AppBarCalculatorScreen(
            onClickFavorite = {},
            onClickMore = {},
            isFavorite = false
        )

//        SectionCalculatorContent(
//            input = input,
//            result = result,
//            handleButtonClick = viewModel::handleButtonClick
//        )
    }
}

@Composable
@Preview
fun MainCalculatorScreenPreview() {
    AppUnitTheme {
       CalculatorScreen()
   }
}
