package com.billy.simpleunitconvert

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import com.billy.simpleunitconvert.core.data.repository.init.CreateDatabaseRepository
import com.billy.simpleunitconvert.feature.common.Utils.isEnableAds
import com.billy.simpleunitconvert.core.data.utils.logError
import com.billy.simpleunitconvert.feature.common.AppOpenAdManager
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltAndroidApp
class SimpleUnitConvertApplication : Application(), Application.ActivityLifecycleCallbacks {

    @Inject
    lateinit var createDatabaseRepository: CreateDatabaseRepository
    private lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig

    lateinit var appOpenAdManager: AppOpenAdManager
    private var currentActivity: Activity? = null

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        appScope.launch {
            MobileAds.initialize(this@SimpleUnitConvertApplication)
        }
    }

    override fun onCreate() {
        appOpenAdManager = AppOpenAdManager(this@SimpleUnitConvertApplication)
        appOpenAdManager.loadAd()
        ProcessLifecycleOwner.get().lifecycle.addObserver(appOpenAdManager)

        super.onCreate()


        try {
            initializeDatabase()
            FirebaseApp.initializeApp(this@SimpleUnitConvertApplication)


            if (!BuildConfig.DEBUG) {
                FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = true
            }
            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
            val configSettings =
                FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(12 * 3600)
                    .build()
            mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
            listenerFirebaseRemoteConfig()

        } catch (e: Exception) {
            logError("error application: ${e.message}")
        }


    }

    private fun listenerFirebaseRemoteConfig() {
        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                updateRemoteConfig()
            }
        }
        mFirebaseRemoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                if (configUpdate.updatedKeys.contains(KEY_REMOTE_ADS)) {
                    mFirebaseRemoteConfig.activate().addOnCompleteListener {
                        updateRemoteConfig()
                    }
                }
            }

            override fun onError(p0: FirebaseRemoteConfigException) {
                Log.w("mFirebaseRemoteConfig", "onError: ${p0.message}")
            }

        })
    }

    private fun updateRemoteConfig() {
        val simpleUnitConvert = mFirebaseRemoteConfig.getBoolean(KEY_REMOTE_ADS)
        isEnableAds = simpleUnitConvert
    }

    private fun initializeDatabase() {
        appScope.launch {
            runCatching {
                createDatabaseRepository.readDataSaveToDatabase()
            }.onFailure {
                logError("error readDataSaveToDatabase: ${it.message}")
            }.onSuccess {
                Log.e("initializeDatabase", "success")
            }
        }

        appScope.launch {
            runCatching {
                createDatabaseRepository.insertInformation()
            }.onFailure {
                logError("error insert Uid: ${it.message}")
            }.onSuccess {
                Log.e("insertInformation", "success")
            }
        }
    }

    companion object {
        const val KEY_REMOTE_ADS = "EnableAdsInApp"
        
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        // onActivityCreated
    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
        appOpenAdManager.showAdIfAvailable(activity) {
            // onActivityResumed
        }
    }

    override fun onActivityResumed(activity: Activity) {
        // onActivityResumed
    }

    override fun onActivityPaused(activity: Activity) {
        // onActivityPaused
    }

    override fun onActivityStopped(activity: Activity) {
        // onActivityStopped
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // onActivitySaveInstanceState
    }

    override fun onActivityDestroyed(activity: Activity) {
        // onActivityDestroyed
    }
}
