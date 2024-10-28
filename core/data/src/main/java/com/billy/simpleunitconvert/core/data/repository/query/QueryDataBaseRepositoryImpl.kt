package com.billy.simpleunitconvert.core.data.repository.query

import android.util.Log
import com.billy.simpleunitconvert.core.database.UnitDao
import com.billy.simpleunitconvert.core.database.entity.mapper.asDomain
import com.billy.simpleunitconvert.core.network.Dispatcher
import com.billy.simpleunitconvert.core.network.SimpleUnitAppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


internal class QueryDataBaseRepositoryImpl @Inject constructor(
    private val unitDao: UnitDao,
    @Dispatcher(SimpleUnitAppDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher
) : QueryDataBaseRepository {
    override suspend fun queryAllUnit() = flow {
        val allUnit = unitDao.getAllUnitList()
        Log.e("QueryDataBase","allUnit: $allUnit")
        emit(allUnit.asDomain())
    }.flowOn(ioDispatcher)

    override suspend fun queryUnitByCategory(category: String) {
        Log.e("QueryDataBase","queryUnitByCategory")

    }
}
