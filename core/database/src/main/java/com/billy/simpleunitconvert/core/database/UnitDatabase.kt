package com.billy.simpleunitconvert.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.billy.simpleunitconvert.core.database.entity.UnitEntity

@Database(entities = [UnitEntity::class], version = 1, exportSchema = true)

abstract class UnitDatabase : RoomDatabase() {
    abstract fun unitDao(): UnitDao
}
