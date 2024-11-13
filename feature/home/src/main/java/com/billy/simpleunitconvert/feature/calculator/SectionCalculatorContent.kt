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

@Composable
fun SectionCalculatorContent(
    input: String,
    result: String,
    handleButtonClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
            .fillMaxSize()
            .padding(AppUnitTheme.dimens.dp16),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        UnitDisplayBox(
        input,
        result,
        )
        CalculatorButtons(
            onButtonClick = { handleButtonClick() },
            modifier = Modifier.fillMaxWidth()
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
