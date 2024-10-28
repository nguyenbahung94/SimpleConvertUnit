package com.billy.simpleunitconvert.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.billy.simpleunitconvert.core.database.entity.HomeUnitWithUnitConvert
import com.billy.simpleunitconvert.core.database.entity.UnitConvertWithUnitItem
import com.billy.simpleunitconvert.core.database.entity.UnitEntity

@Dao
interface UnitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnitList(unitList: List<UnitEntity>)

    @Query("SELECT * FROM UnitEntity WHERE symbol = :symbol_")
    suspend fun getUnit(symbol_: String): UnitEntity

    @Query("SELECT * FROM HomeUnitEntity")
    suspend fun getHomeUnitList(): List<HomeUnitWithUnitConvert>

    @Query("SELECT * FROM UnitConvertEntity WHERE category = :category_")
    suspend fun getUnitListByCategory(category_: String): List<UnitConvertWithUnitItem>
}
