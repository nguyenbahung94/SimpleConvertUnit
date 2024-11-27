package com.billy.simpleunitconvert.feature.calculator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.model.search.SearchCategory
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.core.navigation.currentComposeNavigator
import com.billy.simpleunitconvert.feature.common.BorderedActionText
import com.billy.simpleunitconvert.feature.common.LocalCategoryProvider
import com.billy.simpleunitconvert.feature.common.TextSingleLineUnitLayout


@Composable
fun UnitDisplayBox(
    calculatorState: CalculatorState,
    onEvent: (CalculatorEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {

        UnitDisplayNumbers(
            calculatorState.unitInput.name,
            calculatorState.unitInput.symbol,
            calculatorState.calculatorDisplay.input,
            anotherName = calculatorState.unitResult.name,
            isInput = true,
            onEvent = onEvent
        )
        Spacer(modifier = Modifier.height(AppUnitTheme.dimens.dp4))
        Spacer(modifier = Modifier.height(AppUnitTheme.dimens.dp4))
        UnitDisplayNumbers(
            calculatorState.unitResult.name,
            calculatorState.unitResult.symbol,
            calculatorState.calculatorDisplay.result,
            anotherName = calculatorState.unitInput.name,
            onEvent = onEvent,
            isInput = false
        )
        Spacer(modifier = Modifier.height(AppUnitTheme.dimens.dp10))
        CalculatorActionRow(onEvent = onEvent, value = calculatorState.calculatorDisplay.result)
    }
}

@Composable
fun CalculatorActionRow(
    modifier: Modifier = Modifier,
    onEvent: (CalculatorEvent) -> Unit,
    value: String
) {
    val context = LocalContext.current
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        BorderedActionText(
            text = "Swap unit"
        ) {
            onEvent(CalculatorEvent.OnClickSwap)
        }
        Spacer(modifier = Modifier.width(AppUnitTheme.dimens.dp10))
        BorderedActionText(
            text = "Copy unit"
        ) {
            copyUnitToClipboard(context, value)
        }
    }
}

private fun copyUnitToClipboard(context: Context, value: String) {
    val clipboard =context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("value", value)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
}


@Composable
fun UnitDisplayNumbers(
    name: String,
    unit: String,
    value: String,
    anotherName: String,
    isInput: Boolean,
    onEvent: (CalculatorEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                AppUnitTheme.dimens.dp1,
                color = AppUnitTheme.colors.backgroundUnit,
                shape = RoundedCornerShape(8.dp)
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppUnitTheme.dimens.dp8),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // First Row: Unit name on the left, Unit on the right
            DisplayNumbersRow(name = name, unit = unit, isInput = isInput, anotherName = anotherName, onEvent = onEvent )
            Spacer(modifier = Modifier.height(AppUnitTheme.dimens.dp8)) // Space between rows
            // Second Row: Number text centered below the first row
            InputTextDisplay(
                value,
                Modifier
                    .height(AppUnitTheme.dimens.dp35)
                    .wrapContentSize(Alignment.CenterEnd)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun DisplayNumbersRow(
    name: String,
    unit: String,
    anotherName: String,
    isInput: Boolean,
    onEvent: (CalculatorEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val composeNavigator = currentComposeNavigator
    val category = LocalCategoryProvider.current
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        UnitTextBox(text = name,
            color = AppUnitTheme.colors.absoluteWhite,
            showIcon = true,
            modifier = Modifier.clickable {
                onEvent(CalculatorEvent.OnClickOpenSearch(isInput))
                composeNavigator.navigate(SimpleUnitScreen.Search(SearchCategory(category = category?.category, nameIgnore = anotherName)))

            })
        Spacer(modifier = Modifier.weight(1f)) // Spacer for alignment
        UnitTextBox(text = unit, color = AppUnitTheme.colors.absoluteWhite)
    }
}

@Composable
fun InputTextDisplay(value: String, modifier: Modifier = Modifier) {
    TextSingleLineUnitLayout(
        text = value,
        defaultFontSize = AppUnitTheme.dimens.sp24,
        modifier = modifier,
        color = AppUnitTheme.colors.absoluteBlack
    )
}

@Composable
fun UnitTextBox(
    text: String,
    color: Color,
    showIcon: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .background(
                AppUnitTheme.colors.backgroundUnit, shape = RoundedCornerShape(4.dp)
            )
            .border(
                1.dp, color = AppUnitTheme.colors.backgroundUnit, shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = AppUnitTheme.dimens.dp6, vertical = AppUnitTheme.dimens.dp6)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // Aligns text and icon vertically centered
            modifier = modifier.wrapContentWidth()
        ) {
            Text(
                text = text,
                fontSize = AppUnitTheme.dimens.sp16,
                color = color,
            )
            if (showIcon) {
                Spacer(modifier = Modifier.width(AppUnitTheme.dimens.dp4)) // Adds space between text and icon
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = AppUnitTheme.colors.absoluteWhite,
                    modifier = Modifier
                        .size(AppUnitTheme.dimens.dp16)
                        .scale(1.5f)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun UnitDisplayBoxPreview() {
    AppUnitTheme {
        val searchCategory = SearchCategory(category = "volume", nameIgnore = "Cubic meter")
        CompositionLocalProvider(LocalCategoryProvider provides searchCategory) {
            // Your composable content
            UnitDisplayBox(calculatorState = CalculatorState(), onEvent = {})
        }
    }
}
