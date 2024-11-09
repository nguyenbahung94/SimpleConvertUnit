package com.billy.simpleunitconvert.core.data.repository.query

import android.util.Log
import com.billy.simpleunitconvert.core.database.UnitDao
import com.billy.simpleunitconvert.core.database.entity.HomeUnitWithUnitConvert
import com.billy.simpleunitconvert.core.database.entity.mapper.asDomain
import com.billy.simpleunitconvert.core.model.HomeUnit
import com.billy.simpleunitconvert.core.model.UnitConvert
import com.billy.simpleunitconvert.core.model.UnitItemData
import com.billy.simpleunitconvert.core.network.Dispatcher
import com.billy.simpleunitconvert.core.network.SimpleUnitAppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


internal class QueryDataBaseRepositoryImpl @Inject constructor(
    private val unitDao: UnitDao,
    @Dispatcher(SimpleUnitAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : QueryDataBaseRepository {

    override fun queryHomeUnits(): Flow<List<HomeUnit>> = flow {
        val homeUnits = unitDao.getHomeUnitList()
        emit(transformer(homeUnits))
    }.flowOn(ioDispatcher)
        .catch { Log.e("queryHomeUnits", "error: ${it.message}") }

    override  fun queryUnitByKeWord(keyWord: String): Flow<List<UnitItemData>> = flow {
        Log.e("queryUnitByKeWord","keyword: $keyWord")
        val unitItems = unitDao.searchUnitItem(keyWord)
        if (keyWord.isEmpty() || keyWord.isBlank()) {
            emit(unitItems.filter { it.popular }.asDomain())
            return@flow
        }
        emit(unitItems.asDomain())

    }.flowOn(ioDispatcher)
        .catch { Log.e("queryUnitByCategory", "error: ${it.message}")
    }

    private fun transformer(homeUnits: List<HomeUnitWithUnitConvert>): List<HomeUnit> {
        return homeUnits.map { homeUnitWithUnitConvert ->
            val unitConverts = homeUnitWithUnitConvert.unitConverts.map { unitConvert ->
                UnitConvert(
                    unitConvert.image, unitConvert.name, unitConvert.shortName, unitConvert.category
                )
            }
            HomeUnit(homeUnitWithUnitConvert.homeUnit.groupName, homeUnitWithUnitConvert.homeUnit.shortName,  unitConverts)
        }
    }
}
