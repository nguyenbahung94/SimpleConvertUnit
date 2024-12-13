package com.billy.simpleunitconvert.core.data.repository.init

interface CreateDatabaseRepository {
    suspend fun readDataSaveToDatabase()
    suspend fun insertInformation()
    suspend fun getCountOpenApp(): Int
    suspend fun updateCountOpenApp(countOpenApp: Int)
    suspend fun setFlagEnableAds(isEnableAds: Boolean)
    suspend fun isEnableAds(): Boolean
}
