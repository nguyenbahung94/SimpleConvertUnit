package com.billy.simpleunitconvert.feature.feedback

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.colors
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.dimens
import com.billy.simpleunitconvert.core.navigation.currentComposeNavigator

@Composable
fun FeedbackScreen() {
    var rating by remember { mutableIntStateOf(0) }
    var featureRequest by remember { mutableStateOf("") }
    var appExperience by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val composeNavigator = currentComposeNavigator

    Scaffold(containerColor = colors.primary.copy(alpha = 0.1f), topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colors.primary.copy(alpha = 0.1f),
            titleContentColor = colors.black,
        ), title = { Text(text = "Feedback") }, navigationIcon = {
            IconButton(onClick = {
                composeNavigator.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }, actions = { })
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = dimens.dp16)
                .verticalScroll(rememberScrollState())
                .clickable {
                    keyboardController?.hide()
                }, horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Rate your experience",
                style = TextStyle.Default.copy(
                    fontWeight = FontWeight.SemiBold, color = colors.black
                ),
                modifier = Modifier.padding(dimens.dp8),
                fontSize = dimens.sp28
            )

            Row(
                modifier = Modifier.padding(dimens.dp8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    IconButton(onClick = { rating = index + 1 }) {
                        Icon(
                            imageVector = if (index < rating) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Rating star",
                            tint = if (index < rating) colors.colorStart else Color.Gray,
                            modifier = Modifier.size(dimens.dp50)
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.size(dimens.dp20))
            Text(
                text = "Request a feature",
                fontWeight = FontWeight.Normal,
                modifier = Modifier.align(Alignment.Start),
                fontSize = dimens.sp18
            )
            Spacer(modifier = Modifier.size(dimens.dp8))
            TextField(
                value = featureRequest,
                onValueChange = { featureRequest = it },
                textStyle = TextStyle.Default.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = dimens.sp16,
                    color = colors.absoluteBlack
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = dimens.dp100)
                    .border(
                        width = 1.dp,
                        color = colors.backgroundUnit,
                        shape = RoundedCornerShape(dimens.dp8)
                    )
                    .clip(RoundedCornerShape(dimens.dp8)),
                placeholder = {
                    Text(text = "What feature would you like to see?")
                },
                maxLines = 7,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colors.absoluteWhite.copy(alpha = 0.5f),
                    unfocusedLabelColor = colors.black,
                    unfocusedContainerColor = colors.absoluteWhite.copy(alpha = 0.5f),
                    unfocusedTextColor = colors.black,
                    focusedTextColor = colors.black,
                    focusedLabelColor = colors.black,
                )
            )
            Spacer(modifier = Modifier.size(dimens.dp20))
            Text(
                text = "How was your experience?",
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start),
                fontSize = dimens.sp18
            )
            Spacer(modifier = Modifier.size(dimens.dp8))
            TextField(
                value = appExperience,
                onValueChange = { appExperience = it },
                textStyle = TextStyle.Default.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = AppUnitTheme.dimens.sp16,
                    color = colors.absoluteBlack
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = dimens.dp100)
                    .border(
                        width = 1.dp,
                        color = colors.backgroundUnit,
                        shape = RoundedCornerShape(dimens.dp8)
                    )
                    .clip(RoundedCornerShape(dimens.dp8)),
                placeholder = { Text("Share your thoughts about the app") },
                maxLines = 7,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colors.absoluteWhite.copy(alpha = 0.5f),
                    unfocusedLabelColor = colors.black,
                    unfocusedContainerColor = colors.absoluteWhite.copy(alpha = 0.5f),
                    unfocusedTextColor = colors.black,
                    focusedTextColor = colors.black,
                    focusedLabelColor = colors.black,
                )
            )

            Spacer(modifier = Modifier.size(dimens.dp80))
            Button(
                onClick = {},
                modifier = Modifier
                    .width(200.dp)
                    .height(dimens.dp50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.backgroundUnit,
                    contentColor = colors.white,
                    disabledContentColor = colors.primary
                )
            ) {
                Text(text = "Submit")
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
