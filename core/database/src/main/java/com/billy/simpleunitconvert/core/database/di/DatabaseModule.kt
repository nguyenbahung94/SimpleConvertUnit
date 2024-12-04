package com.billy.simpleunitconvert.core.database.di

import android.content.Context
import androidx.room.Room
import com.billy.simpleunitconvert.core.database.MIGRATION_1_2
import com.billy.simpleunitconvert.core.database.UnitDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UnitDatabase {
        return Room.databaseBuilder(
            context, UnitDatabase::class.java,
            "unit_database.db"
        ).addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    fun provideUnitDao(database: UnitDatabase) = database.unitDao()

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }
}


