package com.billy.simpleunitconvert.core.data.repository.query

import androidx.annotation.WorkerThread
import androidx.paging.PagingData
import com.billy.simpleunitconvert.core.model.home.HomeUnit
import com.billy.simpleunitconvert.core.model.home.UnitConvertData
import com.billy.simpleunitconvert.core.model.home.UnitItemData
import kotlinx.coroutines.flow.Flow

interface QueryDataBaseRepository {

    @WorkerThread
    fun queryHomeUnits(): Flow<List<HomeUnit>>

    @WorkerThread
    fun queryUnitByKeWord(keyWord: String, category: String?):  Flow<PagingData<UnitItemData>>

    @WorkerThread
    fun queryUnitByCategory(category: String): Flow<List<UnitItemData>>

    @WorkerThread
    fun updateFavoriteUnit(category: String, isFavorite: Boolean): Flow<String>

    @WorkerThread
    fun getUnitConvert(category: String): Flow<UnitConvertData>
}
