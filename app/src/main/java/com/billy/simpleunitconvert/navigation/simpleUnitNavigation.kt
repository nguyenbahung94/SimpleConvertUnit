package com.billy.simpleunitconvert.navigation

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen


fun NavGraphBuilder.simpleUnitNavigation() {
    composable<SimpleUnitScreen.Home> {
     //   SimpleUnitHome()
    }
}
