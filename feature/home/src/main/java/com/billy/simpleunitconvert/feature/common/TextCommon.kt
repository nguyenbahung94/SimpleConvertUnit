package com.billy.simpleunitconvert.feature.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.billy.simpleunitconvert.core.designsystem.theme.SimpleConvertUnitTheme

@Composable
fun TextUnitCommon(
    name: String,
    fontSize: TextUnit = 16.sp,
    padding: Int = 12,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = SimpleConvertUnitTheme.colors.subtitle
) {
    Text(
        text = name,
        style = style,
        fontSize = fontSize,
        color = color,
        modifier = Modifier.padding(start = padding.dp)
    )
}
