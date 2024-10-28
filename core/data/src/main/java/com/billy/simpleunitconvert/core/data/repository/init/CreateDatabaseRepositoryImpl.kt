package com.billy.simpleunitconvert.core.data.repository.init

import android.content.Context
import android.util.Log
import com.billy.simpleunitconvert.core.database.UnitDao
import com.billy.simpleunitconvert.core.database.entity.mapper.asEntity
import com.billy.simpleunitconvert.core.model.UnitsData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal class CreateDatabaseRepositoryImpl @Inject constructor(
    private val unitDao: UnitDao,
    @ApplicationContext private val context: Context,
    private val json: Json
): CreateDatabaseRepository {

    override suspend fun readDataSaveToDatabase() {
      runCatching {
          val jsonString = context.assets.open("length.json").bufferedReader().use { it.readText() }
          Log.e("readDataSaveToDatabase","jsonString: $jsonString")

          val unitList = json.decodeFromString<UnitsData>(jsonString)

          val allUnits = unitList.unitLength + unitList.unitCount

          Log.e("readDataSaveToDatabase","allUnits: $allUnits")

          val unitEntites = allUnits.asEntity()
          Log.e("readDataSaveToDatabase","unitEntites: $unitEntites")
          unitDao.insertUnitList(unitEntites)

      }.onFailure {
          Log.e("readDataSaveToDatabase","error: ${it.message}")
      }.onSuccess {
            Log.e("readDataSaveToDatabase","success")
      }
    }
}
