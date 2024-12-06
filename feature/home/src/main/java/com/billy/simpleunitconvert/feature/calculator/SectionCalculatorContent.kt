package com.billy.simpleunitconvert.feature.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.model.search.SearchCategory
import com.billy.simpleunitconvert.feature.common.BannerAdView
import com.billy.simpleunitconvert.feature.common.LocalCategoryProvider
import com.billy.simpleunitconvert.feature.common.Utils

@Composable
fun SectionCalculatorContent(
    calculatorState: CalculatorState,
    onEvent: (CalculatorEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .testTag("UnitCalculate")
            .fillMaxWidth()
            .fillMaxSize()
            .padding(horizontal = AppUnitTheme.dimens.dp16),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(AppUnitTheme.dimens.dp16))
        CompositionLocalProvider(LocalCategoryProvider provides SearchCategory(calculatorState.category)) {
            UnitDisplayBox(calculatorState, onEvent, modifier = Modifier.fillMaxWidth().weight(0.5f))
        }
        CalculatorButtons(
            onButtonClick = { onEvent(CalculatorEvent.OnClickButton(it)) },
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
        )
        Spacer(modifier = Modifier.height(AppUnitTheme.dimens.dp5))
        BannerAdView(adUnitId = Utils.ADSID.BANNER) // Replace with your Ad Unit ID
    }
}


@Composable
@Preview(showBackground = true, device = "id:pixel_4", showSystemUi = true)
@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240")
fun SectionCalculatorContentPreview() {
    AppUnitTheme {
        SectionCalculatorContent(
            calculatorState = CalculatorState(),
            onEvent = {}
        )
    }
}
