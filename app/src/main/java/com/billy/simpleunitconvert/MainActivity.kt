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
import com.billy.simpleunitconvert.feature.common.AppOpenAdManager
import com.billy.simpleunitconvert.feature.common.Utils
import com.billy.simpleunitconvert.feature.common.isNetworkAvailable
import com.billy.simpleunitconvert.ui.SimpleUnitMain
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.common.IntentSenderForResultStarter
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
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
        checkForUpdates()
        // Install the splash screen early
        val splashScreen = installSplashScreen().apply {
            setOnExitAnimationListener { splashScreenViewProvider ->
                splashScreenViewProvider.iconView.animate().setDuration(300L).alpha(0f)
                    .withEndAction { splashScreenViewProvider.remove() }.start()
            }
        }
        lifecycleScope.launch {
            val count = createDatabaseRepository.getCountOpenApp()
            val isEnableAds = createDatabaseRepository.isEnableAds()
            Log.e("MainActivity", "count: $count")
            Log.e("MainActivity", "isEnableAds: $isEnableAds")
            if (isNetworkAvailable() && isEnableAds && count >= 1) {
                createDatabaseRepository.updateCountOpenApp(0)
                Utils.isJustShowOpenApp = true
                displayAdsOpen(splashScreen)
            } else {
                Log.e("MainActivity", "initializeAppContent")
                initializeAppContent()
                createDatabaseRepository.updateCountOpenApp(count + 1)
            }
        }
    }

    private fun checkForUpdates() {
        if (!checkAppUpdateAvailability()) return
        try {
            appUpdateManager = AppUpdateManagerFactory.create(this)
        } catch (e: Exception) {
            logError("$e,Error in app update manager")
        }

        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(
                    AppUpdateType.IMMEDIATE
                )
            ) {
                try {
                    val starter =
                        IntentSenderForResultStarter { intent, _, fillInIntent, flagsMask, flagsValues, _, _ ->
                            val request =
                                IntentSenderRequest.Builder(intent).setFillInIntent(fillInIntent)
                                    .setFlags(flagsValues, flagsMask).build()

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

    private fun checkAppUpdateAvailability(): Boolean {
        try {
            val resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
            return when (resultCode) {
                ConnectionResult.SUCCESS -> {
                    true
                }

                else -> {
                    false
                }
            }
        } catch (e: Exception) {
            logError("Error in checkAppUpdateAvailability: $e")
            return false
        }
    }

    private fun displayAdsOpen(splashScreen: SplashScreen) {
        Log.e("MainActivity", "displayAdsOpen")
        val appOpenAdManager = (applicationContext as SimpleUnitConvertApplication).appOpenAdManager
        lifecycleScope.launch {
            splashScreen.setKeepVisibleCondition {
                !appOpenAdManager.isShowingAd// Keep splash until ad is ready
            }
            delay(2000)
            Handler(Looper.getMainLooper()).postDelayed({
                appOpenAdManager.showAdIfAvailable(this@MainActivity) {
                    // Proceed with app initialization after ad
                    initializeAppContent()
                }
            }, 2500)
        }
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
