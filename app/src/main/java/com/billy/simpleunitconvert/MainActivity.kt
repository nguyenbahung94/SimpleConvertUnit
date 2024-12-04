package com.billy.simpleunitconvert

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.lifecycleScope
import com.billy.simpleunitconvert.core.data.repository.init.CreateDatabaseRepository
import com.billy.simpleunitconvert.core.navigation.AppComposeNavigator
import com.billy.simpleunitconvert.core.navigation.LocalComposeNavigator
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.ui.SimpleUnitMain
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var composeNavigator: AppComposeNavigator<SimpleUnitScreen>

    @Inject
    lateinit var createDatabaseRepository: CreateDatabaseRepository

    private lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(0)
            .build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        setContent {
            CompositionLocalProvider(
                LocalComposeNavigator provides composeNavigator
            ) {
                SimpleUnitMain(composeNavigator = composeNavigator)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        listenerFirebaseRemoteConfig()
    }

    private fun listenerFirebaseRemoteConfig() {
        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val simpleUnitConvert = mFirebaseRemoteConfig.getBoolean("AppSimpleUnitConvert")
                    lifecycleScope.launch(Dispatchers.IO) {
                        runCatching {
                            createDatabaseRepository.updateRemoteConfig(simpleUnitConvert)
                        }.onFailure {
                            createDatabaseRepository.updateRemoteConfig(false)
                        }.onSuccess {
                            Log.e("mFirebaseRemoteConfig", "onSuccess")
                        }
                    }
                } else {
                    Log.e("mFirebaseRemoteConfig", "Fetch failed")
                }
            }
    }
}
