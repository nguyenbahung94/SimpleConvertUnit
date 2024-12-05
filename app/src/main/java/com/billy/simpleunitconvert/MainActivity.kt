package com.billy.simpleunitconvert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.billy.simpleunitconvert.core.data.repository.init.CreateDatabaseRepository
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

    @Inject
    lateinit var createDatabaseRepository: CreateDatabaseRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            CompositionLocalProvider(
                LocalComposeNavigator provides composeNavigator
            ) {
                SimpleUnitMain(composeNavigator = composeNavigator)
            }
        }
    }
}
