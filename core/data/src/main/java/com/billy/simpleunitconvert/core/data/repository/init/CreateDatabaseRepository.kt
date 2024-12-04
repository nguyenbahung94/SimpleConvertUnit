package com.billy.simpleunitconvert.core.data.repository.init

interface CreateDatabaseRepository {
    suspend fun readDataSaveToDatabase()
    suspend fun insertInformation()
    suspend fun updateRemoteConfig(value: Boolean)
}
