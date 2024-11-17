package com.billy.simpleunitconvert.feature.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.designsystem.utils.LogCompositions


@Composable
fun SectionCalculatorContent(
    calculatorState: CalculatorState,
    onEvent: (CalculatorEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LogCompositions("SectionCalculatorContent", "SectionCalculatorContent")
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxSize()
            .padding(AppUnitTheme.dimens.dp16),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        UnitDisplayBox(calculatorState)
        CalculatorButtons(
            onButtonClick = { onEvent(CalculatorEvent.OnClickButton(it)) },
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
        )
    }
}


@Composable
@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Preview(showBackground = true, device = "spec:width=350dp,height=756dp")
@Preview(showBackground = true, device = "spec:width=673dp,height=841dp")
@Preview(showBackground = true, device = "id:pixel_4", showSystemUi = true)
@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240")
fun SectionCalculatorContentPreview() {
    AppUnitTheme {

    }
}
