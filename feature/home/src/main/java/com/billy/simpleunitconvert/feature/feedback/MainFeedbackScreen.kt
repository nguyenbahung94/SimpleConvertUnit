package com.billy.simpleunitconvert.feature.feedback

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.colors
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.dimens
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.core.navigation.currentComposeNavigator
import com.billy.simpleunitconvert.feature.common.TitleCommon

@Composable
fun FeedbackScreen() {
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
            RatingStar()

            Spacer(modifier = Modifier.size(dimens.dp8))

            InputFeedback( onFeatureRequestChange = {}, onAppExperienceChange = {})

            Spacer(modifier = Modifier.size(dimens.dp80))

            ButtonSubmit( onSubmitted = {
                composeNavigator.navigate(SimpleUnitScreen.Thank) {
                    popUpTo<SimpleUnitScreen.Calculator>()
                }
            })

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
