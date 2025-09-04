package com.billy.simpleunitconvert.feature.common

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.colors
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.dimens

@Composable
fun TextUnitCommon(
    text: String,
    fontSize: TextUnit = 16.sp,
    padding: Int = 12,
    style: TextStyle,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = style,
        fontSize = fontSize,
        modifier = modifier.padding(start = padding.dp),
    )
}

@Composable
fun BorderedActionText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .scale(scale)
            .background(
                color = if (enabled)
                    colors.backgroundUnit.copy(alpha = 0.1f)
                else colors.backgroundUnit.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = if (enabled)
                    colors.backgroundUnit.copy(alpha = 0.2f)
                else colors.backgroundUnit.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(
                    color = colors.backgroundUnit.copy(alpha = 0.8f),
                    bounded = true
                ),
                enabled = enabled,
                onClick = onClick
            )
            .padding(
                horizontal = dimens.dp12,
                vertical = dimens.dp8
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            ),
            color = if (enabled)
                colors.absoluteBlack.copy(alpha = 0.9f)
            else colors.absoluteBlack.copy(alpha = 0.4f),
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun TextSingleLineUnit(
    text: String,
    defaultFontSize: TextUnit = AppUnitTheme.dimens.sp22,
    style: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        fontFeatureSettings = "'tnum' on, 'lnum' on"
    ),
    color: Color = AppUnitTheme.colors.subtitle,
    modifier: Modifier = Modifier,
) {
    var scaledFontSize by remember { mutableStateOf(defaultFontSize) }
    val textMeasurer = rememberTextMeasurer()
    LaunchedEffect(text) {
        // Recalculate font size whenever text changes
        scaledFontSize = defaultFontSize
    }

    Text(text = text,
        maxLines = 1,
        softWrap = false,
        textAlign = TextAlign.End,
        overflow = TextOverflow.Visible,
        style = style.copy(fontSize = scaledFontSize),
        color = color,
        modifier = modifier.onSizeChanged { constraints ->
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
                    } else {
                        scaledFontSize = defaultFontSize
                    }
                }
            })
}

object Setting {
    const val MAX_NUMBER_LENGTH = 26
}

@Composable
fun TextSingleLineUnitLayout(
    text: String,
    defaultFontSize: TextUnit = AppUnitTheme.dimens.sp22,
    style: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        fontFeatureSettings = "'tnum' on, 'lnum' on"
    ),
    color: Color = AppUnitTheme.colors.subtitle,
    modifier: Modifier = Modifier,
) {
    var scaledFontSize by remember { mutableStateOf(defaultFontSize) }

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

@Composable
fun TitleCommon(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle =  TextStyle(
        color = colors.absoluteBlack.copy(alpha = 0.9f),
        fontSize = AppUnitTheme.dimens.sp20,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp
    ),
) {
    Text(
        text = text,
        modifier = modifier.fillMaxWidth(),
        textAlign = textAlign,
        style = style,
    )
}

@Composable
fun HintText(
    text: String
) {
    Text(
        text = text,
        color = colors.black.copy(alpha = 0.5f),
        style = TextStyle(
            fontSize = dimens.sp16,
            fontStyle = FontStyle.Italic
        )
    )
}

