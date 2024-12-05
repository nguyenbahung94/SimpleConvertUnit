package com.billy.simpleunitconvert.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.billy.simpleunitconvert.core.database.entity.HomeUnitEntity
import com.billy.simpleunitconvert.core.database.entity.InformationEntity
import com.billy.simpleunitconvert.core.database.entity.UnitConvertEntity
import com.billy.simpleunitconvert.core.database.entity.UnitItemEntity

@Database(entities = [
    HomeUnitEntity::class,
    UnitConvertEntity::class,
    UnitItemEntity::class,
    InformationEntity::class], version = 2, exportSchema = true)

abstract class UnitDatabase : RoomDatabase() {
    abstract fun unitDao(): UnitDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Check if the original table exists before attempting to migrate
        val tableExists = database.query("SELECT name FROM sqlite_master WHERE type='table' AND name='InformationEntity'").count > 0

        if (tableExists) {
            // Create a new table with the desired schema
            database.execSQL("""
                CREATE TABLE IF NOT EXISTS InformationEntity_new (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    uid TEXT
                )
            """)

            // Copy data from the old table to the new table
            database.execSQL("""
                INSERT INTO InformationEntity_new (id, uid)
                SELECT id, uid FROM InformationEntity
            """)

            // Remove the old table
            database.execSQL("DROP TABLE InformationEntity")

            // Rename the new table to the original table name
            database.execSQL("ALTER TABLE InformationEntity_new RENAME TO InformationEntity")
        } else {
            // If the original table doesn't exist, create the new table with the desired schema
            database.execSQL("""
                CREATE TABLE InformationEntity (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    uid TEXT
                )
            """)
        }
    }
}
