package com.billy.simpleunitconvert.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.billy.simpleunitconvert.core.database.entity.HomeUnitEntity
import com.billy.simpleunitconvert.core.database.entity.HomeUnitWithUnitConvert
import com.billy.simpleunitconvert.core.database.entity.UnitConvertEntity
import com.billy.simpleunitconvert.core.database.entity.UnitConvertWithUnitItem
import com.billy.simpleunitconvert.core.database.entity.UnitItemEntity

@Dao
interface UnitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomeUnits(homeUnitList: List<HomeUnitEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnitConverts(unitConvertEntity: List<UnitConvertEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnitItemEntities(unitConvertEntity: List<UnitItemEntity>)

    @Query("SELECT * FROM HomeUnitEntity")
    suspend fun getHomeUnitList(): List<HomeUnitWithUnitConvert>

    @Query("SELECT * FROM UnitItemEntity WHERE symbol LIKE '%' || :keyword || '%' OR unitName LIKE '%' || :keyword || '%'")
    suspend fun searchUnitItem(keyword: String): List<UnitItemEntity>

    @Query("SELECT * FROM UnitConvertEntity WHERE category = :category_")
    suspend fun getUnitListByCategory(category_: String): List<UnitConvertWithUnitItem>
}
