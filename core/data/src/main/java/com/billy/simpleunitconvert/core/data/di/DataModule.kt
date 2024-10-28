package com.billy.simpleunitconvert.core.data.di

import android.content.Context
import com.billy.simpleunitconvert.core.data.repository.init.CreateDatabaseRepository
import com.billy.simpleunitconvert.core.data.repository.init.CreateDatabaseRepositoryImpl
import com.billy.simpleunitconvert.core.data.repository.query.QueryDataBaseRepository
import com.billy.simpleunitconvert.core.data.repository.query.QueryDataBaseRepositoryImpl
import com.billy.simpleunitconvert.core.database.UnitDao
import com.billy.simpleunitconvert.core.network.Dispatcher
import com.billy.simpleunitconvert.core.network.SimpleUnitAppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Provides
    @Singleton
    fun bindsCreateDatabaseRepository(unitDao: UnitDao, json: Json, @ApplicationContext context: Context): CreateDatabaseRepository {
        return CreateDatabaseRepositoryImpl(unitDao, context, json)
    }

    @Provides
    @Singleton
    fun bindsQueryDatabaseRepository(unitDao: UnitDao, @Dispatcher(SimpleUnitAppDispatchers.IO) dispatcher: CoroutineDispatcher): QueryDataBaseRepository {
        return QueryDataBaseRepositoryImpl(unitDao, dispatcher)
    }
}
