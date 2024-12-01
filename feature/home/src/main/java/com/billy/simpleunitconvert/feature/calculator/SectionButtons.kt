package com.billy.simpleunitconvert.feature.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.colors
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.dimens

@Composable
internal fun CalculatorButtons(
    onButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val buttons = listOf(
        listOf("7", "8", "9"),
        listOf("4", "5", "6"),
        listOf("1", "2", "3"),
        listOf("0", ".", "Del", "AC")
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimens.dp4)
    ) {
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
            ) {
                row.forEach { label ->
                    CalculatorButton(
                        label = label,
                        onButtonClick = { onButtonClick(label) },
                        modifier = Modifier.weight(1f)
                            .padding(dimens.dp4)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    label:String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    // Determine button type for dynamic styling
    val buttonType = when (label) {
        "Del", "AC" -> ButtonType.UTILITY
        "0", "." -> ButtonType.SECONDARY
        else -> ButtonType.NUMERIC
    }

    // Dynamic color palette based on button type
    val backgroundColor = when (buttonType) {
        ButtonType.UTILITY -> colors.backgroundUnit.copy(alpha = 0.2f)
        ButtonType.SECONDARY -> colors.backgroundUnit.copy(alpha = 0.1f)
        ButtonType.NUMERIC -> colors.backgroundUnit.copy(alpha = 0.05f)
    }

    val contentColor = when (buttonType) {
        ButtonType.UTILITY -> colors.backgroundUnit.copy(alpha = 0.7f)
        ButtonType.SECONDARY -> AppUnitTheme.colors.absoluteBlack.copy(alpha = 0.7f)
        ButtonType.NUMERIC -> AppUnitTheme.colors.absoluteBlack
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = colors.backgroundUnit.copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = colors.backgroundUnit.copy(alpha = 0.5f), bounded = true),
                onClick = onButtonClick
            ).fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = when (buttonType) {
                ButtonType.UTILITY -> 16.sp
                ButtonType.SECONDARY -> 20.sp
                ButtonType.NUMERIC -> 22.sp
            },
            fontWeight = when (buttonType) {
                ButtonType.UTILITY -> FontWeight.Medium
                ButtonType.SECONDARY -> FontWeight.SemiBold
                ButtonType.NUMERIC -> FontWeight.Bold
            },
            color = contentColor,
            textAlign = TextAlign.Center,
            softWrap = false,
            maxLines = 1
        )
    }
}
private enum class ButtonType {
    NUMERIC,   // Regular number buttons
    SECONDARY, // Zero and decimal point
    UTILITY    // Del and AC buttons
}

@Composable
@Preview
fun CalculatorButtonsPreview() {
    AppUnitTheme {
        CalculatorButtons(onButtonClick = {})
    }
}
