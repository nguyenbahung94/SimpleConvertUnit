package com.billy.simpleunitconvert.core.data.repository.query

import com.billy.simpleunitconvert.core.model.UnitItem
import kotlinx.coroutines.flow.Flow

interface QueryDataBaseRepository {

    suspend fun queryAllUnit(): Flow<List<UnitItem>>

    suspend fun queryUnitByCategory(category: String)
}
