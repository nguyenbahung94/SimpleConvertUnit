package com.billy.simpleunitconvert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.billy.simpleunitconvert.core.navigation.AppComposeNavigator
import com.billy.simpleunitconvert.core.navigation.LocalComposeNavigator
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.ui.SimpleUnitMain
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var composeNavigator: AppComposeNavigator<SimpleUnitScreen>

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(
                LocalComposeNavigator provides composeNavigator
            ) {
                SimpleUnitMain(composeNavigator = composeNavigator)
            }
        }
    }
}
