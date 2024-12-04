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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.colors
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme.dimens
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.core.navigation.currentComposeNavigator
import com.billy.simpleunitconvert.feature.common.CustomSnackbar
import com.billy.simpleunitconvert.feature.common.SnackbarType
import com.billy.simpleunitconvert.feature.common.TitleCommon
import com.billy.simpleunitconvert.feature.common.isNetworkAvailable
import com.billy.simpleunitconvert.feature.common.showCustomSnackbar
import kotlinx.coroutines.launch

@Composable
fun FeedbackScreen(
    feedbackViewModel: FeedbackViewModel = hiltViewModel(),
) {
    val uiState by feedbackViewModel.uiState.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    val composeNavigator = currentComposeNavigator
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(uiState) {
        uiState.error?.let {
            coroutineScope.launch {
                snackbarHostState.showCustomSnackbar(
                    message = it, type = SnackbarType.INFO
                )
            }
            feedbackViewModel.clearError()
        }

        uiState.success?.let {
            if (it) {
                feedbackViewModel.clearSuccess()
                composeNavigator.navigate(SimpleUnitScreen.Thank) {
                    popUpTo<SimpleUnitScreen.Calculator> {
                        inclusive = false
                    }
                }
            }
        }
    }


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
    }, snackbarHost = {
        SnackbarHost(hostState = snackbarHostState, snackbar = {
            CustomSnackbar(snackbarData = it)
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

            RatingStar(event = feedbackViewModel::handleEvent)

            Spacer(modifier = Modifier.size(dimens.dp8))

            InputFeedback(event = feedbackViewModel::handleEvent)

            Spacer(modifier = Modifier.size(dimens.dp80))

            ButtonSubmit(onSubmitted = {
                if (context.isNetworkAvailable()) {
                    feedbackViewModel.handleEvent(FeedbackEvent.SubmitEvent)
                } else {
                    feedbackViewModel.updateError(error = "This feature requires internet connection")
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
