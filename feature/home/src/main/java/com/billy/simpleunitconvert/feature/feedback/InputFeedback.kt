package com.billy.simpleunitconvert.feature.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.colors
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.dimens
import com.billy.simpleunitconvert.feature.common.HintText

@Composable
fun InputFeedback(
    event: (FeedbackEvent) -> Unit,
) {
    var featureRequest by remember { mutableStateOf("") }
    var appExperience by remember { mutableStateOf("") }

    Column() {
        Text(
            text = "Request a Feature", style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = dimens.sp18,
                color = colors.black.copy(alpha = 0.8f),
                letterSpacing = 0.5.sp
            ), modifier = Modifier
                .padding(bottom = dimens.dp8)
                .align(Alignment.Start)
        )

        TextField(
            value = featureRequest,
            onValueChange = {
                featureRequest = it
                event(FeedbackEvent.FeatureRequestEvent(it))
            },
            textStyle = TextStyle(
                fontWeight = FontWeight.Normal, fontSize = dimens.sp16, color = colors.absoluteBlack
            ),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = dimens.dp100, max = dimens.dp200)
                .background(
                    color = colors.absoluteWhite.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = colors.backgroundUnit,
                    shape = RoundedCornerShape(dimens.dp12)
                )
                .clip(RoundedCornerShape(dimens.dp12)),
            placeholder = {
                HintText(
                    text = "What feature would you like to see?"
                )
            },
            maxLines = 7,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.size(dimens.dp20))

        Text(
            text = "How was your experience?", style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = dimens.sp18,
                color = colors.black.copy(alpha = 0.8f),
                letterSpacing = 0.5.sp
            ), modifier = Modifier
                .padding(bottom = dimens.dp8)
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.size(dimens.dp8))

        TextField(
            value = appExperience,
            onValueChange = {
                appExperience = it
                event(FeedbackEvent.AppExperienceEvent(it))
            },
            textStyle = TextStyle.Default.copy(
                fontWeight = FontWeight.Normal, fontSize = dimens.sp16, color = colors.absoluteBlack
            ),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = dimens.dp100, max = dimens.dp200)
                .background(
                    color = colors.absoluteWhite.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = colors.backgroundUnit,
                    shape = RoundedCornerShape(dimens.dp12)
                )
                .clip(RoundedCornerShape(dimens.dp12)),
            placeholder = { HintText("Share your thoughts about the app") },
            maxLines = 7,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}
