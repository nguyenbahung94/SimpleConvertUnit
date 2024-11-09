package com.billy.simpleunitconvert

import android.app.Application
import android.util.Log
import com.billy.simpleunitconvert.core.data.repository.init.CreateDatabaseRepository
import com.billy.simpleunitconvert.core.data.repository.query.QueryDataBaseRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class SimpleUnitConvertApplication: Application() {

    @Inject
    lateinit var createDatabaseRepository: CreateDatabaseRepository

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        initializeDatabase()
    }

    private fun initializeDatabase() {
       appScope.launch {
          runCatching {
              createDatabaseRepository.readDataSaveToDatabase()
          }.onFailure {
              Log.e("initializeDatabase","error: ${it.message}")
          }.onSuccess {
              Log.e("initializeDatabase","success")
          }
       }
    }
}
