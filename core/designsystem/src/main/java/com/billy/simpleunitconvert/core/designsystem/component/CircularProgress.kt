package com.billy.simpleunitconvert.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.billy.simpleunitconvert.core.designsystem.theme.AppUnitTheme

@Composable
fun BoxScope.CircularProgress() {
    CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center),
        color = AppUnitTheme.colors.primary,
    )
}

@Preview
@Composable
private fun CircularProgressPreview() {
    AppUnitTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgress()
        }
    }
}
