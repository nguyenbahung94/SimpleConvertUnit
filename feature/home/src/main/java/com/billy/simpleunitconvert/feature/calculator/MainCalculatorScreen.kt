package com.billy.simpleunitconvert.feature.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme

@Composable
fun CalculatorScreen() {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        AppBarCalculatorScreen(
            onClickFavorite = {},
            onClickMore = {},
            isFavorite = false
        )

        SectionCalculatorContent()
    }
}

@Composable
@Preview
fun MainCalculatorScreenPreview() {
    AppUnitTheme {
       CalculatorScreen()
   }
}
