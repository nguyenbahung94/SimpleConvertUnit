package com.billy.simpleunitconvert.feature.common

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme

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
fun BorderedActionText(
    text: String,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = AppUnitTheme.colors.absoluteBlack,
        modifier = Modifier
            .border(1.dp, AppUnitTheme.colors.backgroundUnit, RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
            .padding(8.dp),
        textAlign = TextAlign.Center,
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
    var scaledFontSize by remember { mutableStateOf(defaultFontSize) }
    val textMeasurer = rememberTextMeasurer()
    LaunchedEffect(text) {
        // Recalculate font size whenever text changes
        scaledFontSize = defaultFontSize
    }

    Text(
        text = text,
        maxLines = 1,
        softWrap = false,
        textAlign = TextAlign.End,
        overflow = TextOverflow.Visible,
        style = style.copy(fontSize = scaledFontSize),
        color = color,
        modifier = modifier
            .onSizeChanged { constraints ->
                if (constraints.width > 0) {

                    val textLayoutResult = textMeasurer.measure(
                        text = text,
                        style = style.copy(fontSize = defaultFontSize),
                        maxLines = 1,
                        softWrap = false
                    )

                    val textWidth = textLayoutResult.size.width
                    if (textWidth > constraints.width) {
                        val scaleFactor = constraints.width / textWidth
                        scaledFontSize = (defaultFontSize.value * scaleFactor).sp
                        Log.e("textChange", "scaledFontSize change = $scaledFontSize")
                    } else {
                        scaledFontSize = defaultFontSize
                        Log.e("textChange", "scaledFontSize reset = $scaledFontSize")
                    }
                }
            }
    )
}




object Setting {
    const val MAX_NUMBER_LENGTH = 28
}

@Composable
fun TextSingleLineUnitLayout(
    text: String,
    defaultFontSize: TextUnit = AppUnitTheme.dimens.sp22,
    style: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        fontFeatureSettings = "'tnum' on, 'lnum' on"
    ),
    color: Color = AppUnitTheme.colors.subtitle,
    modifier: Modifier = Modifier
) {
    var scaledFontSize by remember { mutableStateOf(defaultFontSize) }
    Log.e("textChange", "text = $text")

    SubcomposeLayout(modifier = modifier) { constraints ->
        // First pass: measure with default font size
        val looseConstraints = constraints.copy(maxWidth = Constraints.Infinity)
        val textPlaceable = subcompose("measure") {
            Text(
                text = text,
                maxLines = 1,
                softWrap = false,
                textAlign = TextAlign.End,
                style = style.copy(fontSize = defaultFontSize),
                color = color,
            )
        }[0].measure(looseConstraints)

        // Calculate new font size if needed
        val width = textPlaceable.width
        if (constraints.maxWidth in 1..<width) {
            val scaleFactor = constraints.maxWidth.toFloat() / width.toFloat()
            scaledFontSize = (defaultFontSize.value * scaleFactor).sp
        } else {
            scaledFontSize = defaultFontSize
        }

        // Second pass: measure with final font size
        val finalTextPlaceable = subcompose("final") {
            Text(
                text = text,
                maxLines = 1,
                softWrap = false,
                textAlign = TextAlign.End,
                style = style.copy(fontSize = scaledFontSize),
                color = color,
            )
        }[0].measure(constraints)

        layout(constraints.maxWidth, finalTextPlaceable.height) {
            finalTextPlaceable.place(0, 0)
        }
    }
}


