package com.billy.simpleunitconvert.feature.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.feature.common.TextSingleLineUnit


@Composable
fun UnitDisplayBox() {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        UnitDisplayNumbers()
        Spacer(modifier = Modifier.height(AppUnitTheme.dimens.dp4))
        Icon(
            painter = painterResource(com.billy.simpleunitconvert.core.designsystem.R.drawable.icon_swap2),
            contentDescription = "swap",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(AppUnitTheme.dimens.dp26)
                .align(Alignment.CenterHorizontally)
                .rotate(90f),
        )
        Spacer(modifier = Modifier.height(AppUnitTheme.dimens.dp4))
        UnitDisplayNumbers()
    }
}


@Composable
fun UnitDisplayNumbers() {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(AppUnitTheme.dimens.dp90)
            .border(AppUnitTheme.dimens.dp2, color = AppUnitTheme.colors.backgroundUnit, shape = RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = AppUnitTheme.dimens.dp8, end = AppUnitTheme.dimens.dp8),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // First Row: Unit name on the left, Unit on the right
            DisplayNumbersRow("Length", "m")
            Spacer(modifier = Modifier.height(AppUnitTheme.dimens.dp8)) // Space between rows
            // Second Row: Number text centered below the first row
            InputTextDisplay("11111111111111111234567890123412342256782222222229012.2.2.1221222")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = AppUnitTheme.dimens.dp8, end = AppUnitTheme.dimens.dp8),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // First Row: Unit name on the left, Unit on the right
            DisplayNumbersRow("Length", "m")
            Spacer(modifier = Modifier.height(AppUnitTheme.dimens.dp8)) // Space between rows
            // Second Row: Number text centered below the first row
            InputTextDisplay("11111111111111111234567890123412342256782222222229012.2.2.1221222")
        }
    }
}

@Composable
fun DisplayNumbersRow(name: String, unit: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UnitTextBox(text = name, color = AppUnitTheme.colors.absoluteWhite, showIcon = true)
        Spacer(modifier = Modifier.weight(1f)) // Spacer for alignment
        UnitTextBox(text = unit, color = AppUnitTheme.colors.absoluteWhite)
    }
}

@Composable
fun InputTextDisplay(text: String) {
    TextSingleLineUnit(
        text = text,
        defaultFontSize = AppUnitTheme.dimens.sp23,
        modifier = Modifier
            .fillMaxWidth(),
        color = AppUnitTheme.colors.absoluteBlack
    )
}

@Composable
fun UnitTextBox(text: String, color: Color, showIcon: Boolean = false) {
    Box(
        modifier = Modifier
            .background(AppUnitTheme.colors.backgroundUnit, shape = RoundedCornerShape(4.dp))
            .border(1.dp, color = AppUnitTheme.colors.backgroundUnit, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = AppUnitTheme.dimens.dp6, vertical = AppUnitTheme.dimens.dp6)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, // Aligns text and icon vertically centered
            modifier = Modifier.wrapContentWidth()
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
                    modifier = Modifier.size(AppUnitTheme.dimens.dp16).scale(1.5f)
                )
            }
        }
    }
}

@Composable
@PreviewScreenSizes
@Preview(showBackground = true)
fun UnitDisplayBoxPreview() {
    AppUnitTheme {
        UnitDisplayBox()
    }
}
