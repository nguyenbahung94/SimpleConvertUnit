package com.billy.simpleunitconvert.core.data.repository.query

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.billy.simpleunitconvert.core.data.utils.logError
import com.billy.simpleunitconvert.core.database.UnitDao
import com.billy.simpleunitconvert.core.database.entity.HomeUnitWithUnitConvert
import com.billy.simpleunitconvert.core.database.entity.UnitItemEntity
import com.billy.simpleunitconvert.core.database.entity.mapper.asDomain
import com.billy.simpleunitconvert.core.model.home.HomeUnit
import com.billy.simpleunitconvert.core.model.home.UnitConvert
import com.billy.simpleunitconvert.core.model.home.UnitConvertData
import com.billy.simpleunitconvert.core.model.home.UnitItemData
import com.billy.simpleunitconvert.core.network.Dispatcher
import com.billy.simpleunitconvert.core.network.SimpleUnitAppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject


internal class QueryDataBaseRepositoryImpl @Inject constructor(
    private val unitDao: UnitDao,
    @Dispatcher(SimpleUnitAppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : QueryDataBaseRepository {

    override fun updateFavoriteUnit(category: String, isFavorite: Boolean): Flow<String> = flow {
        val unitConvert = unitDao.updateFavoriteUnit(category, isFavorite)
        if (unitConvert > 0) {
            emit("success")
        } else {
            emit("failed")
        }
    }.flowOn(ioDispatcher).catch { logError("error updateFavoriteUnit: ${it.message}") }

    override fun getUnitConvert(category: String): Flow<UnitConvertData> = flow {
        val unitConvert = unitDao.getUnitConvert(category)
        emit(unitConvert.asDomain())
    }.flowOn(ioDispatcher).catch { logError("error getUnitConvert: ${it.message}") }

    override fun getInformation(): Flow<String?> = flow {
        val information = unitDao.getInformation()
        emit(information?.uid)
    }.flowOn(ioDispatcher).catch { logError("error getInformation: ${it.message}") }

    override fun queryHomeUnits(): Flow<List<HomeUnit>> {
        return combine(
            unitDao.getHomeUnitList(), unitDao.getFavoriteUnit()
        ) { homeUnits, favoriteUnits ->

            homeUnits.forEach { itemHomeUnit ->
                if (itemHomeUnit.homeUnit.shortName == "FAV") {
                    itemHomeUnit.unitConverts = favoriteUnits
                } else {
                    itemHomeUnit.unitConverts = itemHomeUnit.unitConverts.filter { unitConvert ->
                        !unitConvert.isFavorite
                    }
                }
            }
            transformer(homeUnits)
        }.flowOn(ioDispatcher).catch { logError("error queryHome: ${it.message}") }
    }

    override fun queryUnitByKeWord(
        keyWord: String,
        category: String?,
        includeName: Boolean,
        includeSymbol: Boolean,
        includeCategory: Boolean,
    ): Flow<PagingData<UnitItemData>> {
        Log.e("HungLog", "keyWord: $keyWord")
        Log.e("HungLog", "category: $category")
        Log.e("HungLog", "includeName: $includeName")
        Log.e("HungLog", "includeSymbol: $includeSymbol")
        Log.e("HungLog", "includeCategory: $includeCategory")
        return Pager(config = PagingConfig(
            pageSize = 15,
            initialLoadSize = 15,
            prefetchDistance = 3,
            enablePlaceholders = false
        ), pagingSourceFactory = {
            if (category.isNullOrEmpty()) {
                unitDao.searchUnitItem(
                    keyword = keyWord,
                    includeSymbol = if (includeSymbol) 1 else 0,
                    includeName = if (includeName) 1 else 0,
                    includeCategory = if (includeCategory) 1 else 0
                )
            } else {
                unitDao.searchUnitItemInCategory(
                    keyword = keyWord, category = category
                )
            }
        }).flow.map { pagingData -> pagingData.map { transformer(it) } }.flowOn(ioDispatcher)
            .catch { logError("error queryUnitByKeWord: ${it.message}") }
    }

    override fun queryUnitByCategory(category: String): Flow<List<UnitItemData>> = flow {
        val unitListByCategory = unitDao.getUnitListByCategory(category)
        emit(unitListByCategory.unitItems.asDomain())
    }.flowOn(ioDispatcher).catch { logError("error queryUnitByCategory: ${it.message}") }


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
            HomeUnit(
                homeUnitWithUnitConvert.homeUnit.groupName,
                homeUnitWithUnitConvert.homeUnit.shortName,
                unitConverts
            )
        }
    }
}
