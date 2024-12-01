package com.billy.simpleunitconvert.feature.feedback

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.colors
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.dimens

@Composable
fun RatingStar(
    event: (FeedbackEvent) -> Unit,
) {

    var rating by remember { mutableIntStateOf(0) }
    val animatedRating by animateIntAsState(
        targetValue = rating, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium
        )
    )
    Text(
        text = "Rate Your Experience", style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = dimens.sp28,
            color = colors.black.copy(alpha = 0.8f),
            letterSpacing = 0.5.sp
        ), modifier = Modifier
            .wrapContentWidth()
            .padding(bottom = dimens.dp4)
    )


    Row(
        modifier = Modifier.padding(dimens.dp4), verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) { index ->
            val starModifier =
                Modifier
                    .scale(if (index < animatedRating) 1.1f else 1f)
                    .graphicsLayer {
                        alpha = if (index < animatedRating) 1f else 0.5f
                    }
                    .padding(horizontal = dimens.dp4)

            IconButton(onClick = {
                rating = index + 1
                event(FeedbackEvent.RatingEvent(rating))
            }) {
                Icon(
                    imageVector = if (index < rating) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "Rating star",
                    tint = if (index < rating) colors.colorStart else Color.Gray,
                    modifier = starModifier.size(dimens.dp50)
                )
            }
        }

    }
}
