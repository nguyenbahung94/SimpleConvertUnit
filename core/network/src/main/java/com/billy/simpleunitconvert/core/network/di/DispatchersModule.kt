package com.billy.simpleunitconvert.core.network.di

import com.billy.simpleunitconvert.core.network.Dispatcher
import com.billy.simpleunitconvert.core.network.SimpleUnitAppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal object DispatchersModule {

  @Provides
  @Dispatcher(SimpleUnitAppDispatchers.IO)
  fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

  @Provides
  @Dispatcher(SimpleUnitAppDispatchers.DEFAULT)
  fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

  @Provides
  @Dispatcher(SimpleUnitAppDispatchers.UNCONFINED)
    fun providesUndefinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}
