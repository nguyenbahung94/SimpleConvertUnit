package com.billy.simpleunitconvert

import android.app.Application
import android.util.Log
import com.billy.simpleunitconvert.core.data.repository.init.CreateDatabaseRepository
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
        FirebaseApp.initializeApp(this)
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings =
            FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(0).build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        listenerFirebaseRemoteConfig()
    }

    private fun listenerFirebaseRemoteConfig() {
        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                updateRemoteConfig()
            } else {
                Log.e("mFirebaseRemoteConfig", "Fetch failed")
            }
        }
        mFirebaseRemoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                if (configUpdate.updatedKeys.contains("AppSimpleUnitConvert")) {
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
        val simpleUnitConvert = mFirebaseRemoteConfig.getBoolean("AppSimpleUnitConvert")
        appScope.launch(Dispatchers.IO) {
            runCatching {
                createDatabaseRepository.updateRemoteConfig(simpleUnitConvert)
            }.onFailure {
                createDatabaseRepository.updateRemoteConfig(false)
            }.onSuccess {
                Log.e("mFirebaseRemoteConfig", "onSuccess")
            }
        }
    }

    private fun initializeDatabase() {
        appScope.launch {
            runCatching {
                createDatabaseRepository.readDataSaveToDatabase()
            }.onFailure {
                Log.e("initializeDatabase", "error: ${it.message}")
            }.onSuccess {
                Log.e("initializeDatabase", "success")
            }
        }

        appScope.launch {
            runCatching {
                createDatabaseRepository.insertInformation()
            }.onFailure {
                Log.e("insertInformation", "error: ${it.message}")
            }.onSuccess {
                Log.e("insertInformation", "success")
            }
        }
    }
}
