package com.billy.simpleunitconvert.feature.common

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.feature.common.Setting.MAX_NUMBER_LENGTH

@Composable
fun TextUnitCommon(
    text: String,
    fontSize: TextUnit = 16.sp,
    padding: Int = 12,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = AppUnitTheme.colors.subtitle,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = style,
        fontSize = fontSize,
        color = color,
        modifier = modifier.padding(start = padding.dp),
    )
}

@Composable
fun TextSingleLineUnit(
    text: String,
    defaultFontSize: TextUnit = AppUnitTheme.dimens.sp22,
    style: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        fontFeatureSettings = "'tnum' on, 'lnum' on"
    ),
    color: Color = AppUnitTheme.colors.subtitle,
    modifier: Modifier = Modifier
) {
    Log.e("TextSingleLineUnit","defaultFontSize = $defaultFontSize")
    Text(
        text = text.take(MAX_NUMBER_LENGTH),
        maxLines = 1,
        softWrap = false,
        textAlign = TextAlign.End,
        overflow = TextOverflow.Visible,
        style = style.copy(fontSize = defaultFontSize), // Update the style with the adjusted font size
        color = color,
        modifier = modifier
    )
}

object Setting {
    const val MAX_NUMBER_LENGTH = 28
}

object NavArgs {
    const val UNIT_CATEGORY = "unit_category"
}
