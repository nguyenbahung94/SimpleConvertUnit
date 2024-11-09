package com.billy.simpleunitconvert.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.billy.simpleunitconvert.core.database.entity.HomeUnitEntity
import com.billy.simpleunitconvert.core.database.entity.UnitConvertEntity
import com.billy.simpleunitconvert.core.database.entity.UnitItemEntity

@Database(entities = [
    HomeUnitEntity::class,
    UnitConvertEntity::class,
    UnitItemEntity::class], version = 1, exportSchema = true)

abstract class UnitDatabase : RoomDatabase() {
    abstract fun unitDao(): UnitDao
}
