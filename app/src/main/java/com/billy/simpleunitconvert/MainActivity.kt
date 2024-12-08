package com.billy.simpleunitconvert

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.billy.simpleunitconvert.core.data.repository.init.CreateDatabaseRepository
import com.billy.simpleunitconvert.core.data.utils.logError
import com.billy.simpleunitconvert.core.navigation.AppComposeNavigator
import com.billy.simpleunitconvert.core.navigation.LocalComposeNavigator
import com.billy.simpleunitconvert.core.navigation.SimpleUnitScreen
import com.billy.simpleunitconvert.feature.common.Utils
import com.billy.simpleunitconvert.feature.common.isNetworkAvailable
import com.billy.simpleunitconvert.ui.SimpleUnitMain
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.common.IntentSenderForResultStarter
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var appUpdateManager: AppUpdateManager

    @Inject
    internal lateinit var composeNavigator: AppComposeNavigator<SimpleUnitScreen>

    @Inject
    lateinit var createDatabaseRepository: CreateDatabaseRepository
    private val UPDATE_REQUEST_CODE = 110101
    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    Toast.makeText(
                        this,
                        "update success",
                        Toast.LENGTH_SHORT,
                    ).show()
                }

                else -> {
                    Toast.makeText(
                        this,
                        "update failed",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        try {
            appUpdateManager = AppUpdateManagerFactory.create(this)
            checkForUpdates()
        } catch (e: Exception) {
            logError("$e,Error in app update manager")
        }
        // Install the splash screen early
        val splashScreen = installSplashScreen()
        lifecycleScope.launch {
            val count = createDatabaseRepository.getCountOpenApp()
            Log.e("current count", "count: $count")
            Log.e("isAdsEnable", "isAdsEnable: ${Utils.isEnableAds}")
            if (isNetworkAvailable() && Utils.isEnableAds && count >= 4) {
                createDatabaseRepository.updateCountOpenApp(0)
                Utils.isJustShowOpenApp = true
                displayAdsOpen(splashScreen)
            } else {
                initializeAppContent()
                createDatabaseRepository.updateCountOpenApp(count + 1)
            }
        }


    }

    private fun checkForUpdates() {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(
                    AppUpdateType.IMMEDIATE)
            ) {
                try {
                    val starter = IntentSenderForResultStarter { intent, _, fillInIntent, flagsMask, flagsValues, _, _ ->
                        val request = IntentSenderRequest.Builder(intent)
                            .setFillInIntent(fillInIntent)
                            .setFlags(flagsValues, flagsMask)
                            .build()

                        activityResultLauncher.launch(request)
                    }

                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        starter,
                        UPDATE_REQUEST_CODE,
                    )
                } catch (e: Exception) {
                    logError("Error startUpdateFlowForResult: $e")
                }

            }
        }

        appUpdateInfoTask.addOnFailureListener { e ->
            logError("Error in app update manager: $e")
        }
    }

    private fun displayAdsOpen(splashScreen: SplashScreen) {
        val appOpenAdManager = (application as SimpleUnitConvertApplication).appOpenAdManager

        splashScreen.setKeepVisibleCondition {
            !appOpenAdManager.isShowingAd// Keep splash until ad is ready
        }

        Handler(Looper.getMainLooper()).postDelayed({
            appOpenAdManager.showAdIfAvailable(this) {
                // Proceed with app initialization after ad
                initializeAppContent()
            }
        }, 2500)
    }

    private fun initializeAppContent() {
        setContent {
            CompositionLocalProvider(
                LocalComposeNavigator provides composeNavigator
            ) {
                SimpleUnitMain(composeNavigator = composeNavigator)
            }
        }
    }
}
