package com.billy.simpleunitconvert.feature.feedback

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.colors
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.dimens
import com.billy.simpleunitconvert.core.navigation.currentComposeNavigator
import com.billy.simpleunitconvert.feature.common.HintText
import com.billy.simpleunitconvert.feature.common.TitleCommon

@Composable
fun FeedbackScreen() {
    var rating by remember { mutableIntStateOf(0) }
    var featureRequest by remember { mutableStateOf("") }
    var appExperience by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val composeNavigator = currentComposeNavigator
    Scaffold(containerColor = colors.primary, topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colors.primary,
            titleContentColor = colors.black,
        ), title = { TitleCommon(text = "Feedback") }, navigationIcon = {
            IconButton(onClick = {
                composeNavigator.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        })
    }) { paddingValues ->

        val animatedRating by animateIntAsState(
            targetValue = rating,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )

        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimens.dp18)
                .clickable {
                    keyboardController?.hide()
                }, horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Rate Your Experience",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = dimens.sp28,
                    color = colors.black.copy(alpha = 0.8f),
                    letterSpacing = 0.5.sp
                ),
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = dimens.dp4)
            )

            Row(
                modifier = Modifier.padding(dimens.dp4),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    val starModifier = Modifier
                        .scale(if (index < animatedRating) 1.1f else 1f)
                        .graphicsLayer {
                            alpha = if (index < animatedRating) 1f else 0.5f
                        }
                        .padding(horizontal = dimens.dp4)

                    IconButton(onClick = { rating = index + 1 }) {
                        Icon(
                            imageVector = if (index < rating) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Rating star",
                            tint = if (index < rating) colors.colorStart else Color.Gray,
                            modifier =  starModifier.size(dimens.dp50)
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.size(dimens.dp20))

            Text(
                text = "Request a Feature",
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dimens.sp18,
                    color = colors.black.copy(alpha = 0.8f),
                    letterSpacing = 0.5.sp
                ),
                modifier = Modifier.padding(bottom = dimens.dp8).align(Alignment.Start)
            )

            Spacer(modifier = Modifier.size(dimens.dp8))

            TextField(
                value = featureRequest,
                onValueChange = { featureRequest = it },
                textStyle = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = dimens.sp16,
                    color = colors.absoluteBlack
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
                text = "How was your experience?",
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dimens.sp18,
                    color = colors.black.copy(alpha = 0.8f),
                    letterSpacing = 0.5.sp
                ),
                modifier = Modifier.padding(bottom = dimens.dp8).align(Alignment.Start)
            )

            Spacer(modifier = Modifier.size(dimens.dp8))

            TextField(
                value = appExperience,
                onValueChange = { appExperience = it },
                textStyle = TextStyle.Default.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = dimens.sp16,
                    color = colors.absoluteBlack
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

            Spacer(modifier = Modifier.size(dimens.dp80))

            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                colors.backgroundUnit.copy(alpha = 0.8f),
                                colors.backgroundUnit,
                                colors.backgroundUnit.copy(alpha = 0.8f)
                            )
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = colors.backgroundUnit.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable(
                        onClick = {
                            // Submit feedback
                        },
                        indication = ripple(
                            color = colors.backgroundUnit.copy(alpha = 0.8f),
                            bounded = true
                        ),
                        interactionSource = interactionSource
                    )
                    .padding(16.dp)
                    .scale(if (isPressed) 0.95f else 1f)
                    .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Submit",
                    color = Color.White,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        letterSpacing = 1.sp
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FeedbackScreenPreview() {
    AppUnitTheme {
        FeedbackScreen()
    }
}
