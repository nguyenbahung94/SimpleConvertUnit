package com.billy.simpleunitconvert.core.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.billy.simpleunitconvert.core.database.entity.HomeUnitEntity
import com.billy.simpleunitconvert.core.database.entity.HomeUnitWithUnitConvert
import com.billy.simpleunitconvert.core.database.entity.InformationEntity
import com.billy.simpleunitconvert.core.database.entity.UnitConvertEntity
import com.billy.simpleunitconvert.core.database.entity.UnitConvertWithUnitItem
import com.billy.simpleunitconvert.core.database.entity.UnitItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UnitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomeUnits(homeUnitList: List<HomeUnitEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnitConverts(unitConvertEntity: List<UnitConvertEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnitItemEntities(unitConvertEntity: List<UnitItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInformation(informationEntity: InformationEntity)

    @Query("UPDATE UnitConvertEntity SET isFavorite = :isFavorite WHERE category = :category")
    suspend fun updateFavoriteUnit(category: String, isFavorite: Boolean): Int

    @Query("UPDATE INFORMATIONENTITY SET enableAdvertising = :enableAdvertising WHERE id = 1")
    suspend fun updateEnableAdvertising(enableAdvertising: Boolean): Int

    @Query("UPDATE INFORMATIONENTITY SET countOpenApp = :countOpenApp WHERE id = 1")
    suspend fun updateCountOpenApp(countOpenApp: Int): Int

    @Query("SELECT countOpenApp FROM INFORMATIONENTITY WHERE id = 1")
    suspend fun getCountOpenApp(): Int

    @Query("SELECT * FROM HomeUnitEntity")
    fun getHomeUnitList(): Flow<List<HomeUnitWithUnitConvert>>

    @Query(""" SELECT * FROM UnitItemEntity WHERE (symbol LIKE '%' || :keyword || '%' OR unitName LIKE '%' || :keyword || '%') AND (COALESCE(:category, '') = '' OR unitCategory = :category) """)
    fun searchUnitItem(keyword: String, category: String?): PagingSource<Int, UnitItemEntity>

    @Query("SELECT * FROM UnitConvertEntity WHERE category = :category")
    suspend fun getUnitListByCategory(category: String): UnitConvertWithUnitItem

    @Query("SELECT * FROM UnitConvertEntity WHERE category = :category")
    suspend fun getUnitConvert(category: String): UnitConvertEntity

    @Query("SELECT * FROM UnitConvertEntity WHERE isFavorite = 1")
    fun getFavoriteUnit(): Flow<List<UnitConvertEntity>>

    @Query("SELECT * FROM informationentity")
    fun getInformation(): InformationEntity?


}
