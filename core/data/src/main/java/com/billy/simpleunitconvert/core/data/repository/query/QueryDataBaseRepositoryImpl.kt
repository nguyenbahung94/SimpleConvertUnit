package com.billy.simpleunitconvert.core.data.repository.query

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.billy.simpleunitconvert.core.database.UnitDao
import com.billy.simpleunitconvert.core.database.entity.HomeUnitWithUnitConvert
import com.billy.simpleunitconvert.core.database.entity.UnitItemEntity
import com.billy.simpleunitconvert.core.database.entity.mapper.asDomain
import com.billy.simpleunitconvert.core.model.home.HomeUnit
import com.billy.simpleunitconvert.core.model.home.UnitConvert
import com.billy.simpleunitconvert.core.model.home.UnitItemData
import com.billy.simpleunitconvert.core.network.Dispatcher
import com.billy.simpleunitconvert.core.network.SimpleUnitAppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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

    override fun queryUnitByKeWord(keyWord: String, category: String?): Flow<PagingData<UnitItemData>> {
        return Pager(config = PagingConfig(
            pageSize = 15,
            initialLoadSize = 15,
            prefetchDistance = 3,
            enablePlaceholders = false
        ), pagingSourceFactory = {
            Log.e("queryUnitByKeWord", "keyWord: $keyWord, category: $category")
            unitDao.searchUnitItem(keyWord, category)
        }).flow.map { pagingData -> pagingData.map { transformer(it) } }
            .flowOn(ioDispatcher)
            .catch { Log.e("queryUnitByKeWord", "error: ${it.message}") }
    }

    override fun queryUnitByCategory(category: String): Flow<List<UnitItemData>> = flow {
        val unitListByCategory = unitDao.getUnitListByCategory(category)
        emit(unitListByCategory.unitItems.asDomain())
    }.flowOn(ioDispatcher)
        .catch { Log.e("queryUnitByCategory", "error: ${it.message}") }

    private fun transformer(itemEntity: UnitItemEntity): UnitItemData {
        return with(itemEntity) {
            UnitItemData(
                symbol = symbol,
                name = unitName,
                conversionFactor = conversion,
                scaleFactor = scale,
                offset = offset,
                category = unitCategory,
                popular = popular
            )
        }
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
