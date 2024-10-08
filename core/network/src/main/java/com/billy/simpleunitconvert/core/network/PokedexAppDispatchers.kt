package com.billy.simpleunitconvert.core.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val simpleUnitAppDispatchers: SimpleUnitAppDispatchers)

enum class SimpleUnitAppDispatchers {
    IO, DEFAULT, UNCONFINED
}
