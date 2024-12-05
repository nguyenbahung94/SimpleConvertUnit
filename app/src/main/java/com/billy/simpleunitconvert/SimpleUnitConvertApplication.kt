package com.billy.simpleunitconvert

import android.app.Application
import android.util.Log
import com.billy.simpleunitconvert.core.data.repository.init.CreateDatabaseRepository
import com.billy.simpleunitconvert.feature.common.Utils.isEnableAds
import com.billy.simpleunitconvert.core.data.utils.logError
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
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
class SimpleUnitConvertApplication : Application() {

    @Inject
    lateinit var createDatabaseRepository: CreateDatabaseRepository
    private lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig


    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        initializeDatabase()
        FirebaseApp.initializeApp(this@SimpleUnitConvertApplication)

        appScope.launch {
            MobileAds.initialize(this@SimpleUnitConvertApplication)

        }

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings =
            FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(12 * 3600)
                .build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        listenerFirebaseRemoteConfig()
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
        const val KEY_REMOTE_ADS = "AppSimpleUnitConvert"
    }
}
