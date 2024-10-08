package com.billy.simpleunitconvert.core.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.FlowCollector

class ViewModelStateFlowNewVersion<T>(private val key: ViewModelKey, initialValue: T) : MutableStateFlow<T> {

    // Internal MutableStateFlow to store the state
    private val internalStateFlow: MutableStateFlow<Map<ViewModelKey, T>> = MutableStateFlow(mapOf(key to initialValue))

    // Tracks the number of subscribers
    override val subscriptionCount: StateFlow<Int>
        get() = internalStateFlow.subscriptionCount

    // Override value property to manipulate the backing flow
    override var value: T
        get() = internalStateFlow.value.getValue(key)
        set(newValue) {
            internalStateFlow.value = mapOf(key to newValue)
        }

    // Emit a new value with a key, ensuring key correctness
    suspend fun emit(key: ViewModelKey, newValue: T) {
        if (key != this.key) {
            throw IllegalArgumentException(
                "Used different key to emit new value: $value!" +
                        "Don't manipulate key value or try to emit out of ViewModels",
            )
        }
        internalStateFlow.emit(mapOf(key to newValue))
    }

    // Override default emit behavior to prevent misuse
    @RestrictedApi
    override suspend fun emit(value: T) {
        throw IllegalAccessError("Use `emit(key, value)` function instead!")
    }

    // Try emitting a value with a key and ensure key correctness
    fun tryEmit(key: ViewModelKey, newValue: T): Boolean {
        if (key != this.key) {
            throw IllegalArgumentException(
                "Used different key to emit new value: $value!" +
                        "Don't manipulate key value or try to emit out of ViewModels",
            )
        }
        return internalStateFlow.tryEmit(mapOf(key to newValue))
    }

    // Override default tryEmit behavior to prevent misuse
    @RestrictedApi
    override fun tryEmit(value: T): Boolean {
        throw IllegalAccessError("Use `tryEmit(key, value)` function instead!")
    }

    // Override to collect the state value based on the key
    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        internalStateFlow.collect { map ->
            collector.emit(map.getValue(key))
        }
    }

    // Override to reset replay cache if needed (optional)
    override fun resetReplayCache() {
        internalStateFlow.resetReplayCache()
    }

    // Override to provide replay cache of the flow
    override val replayCache: List<T>
        get() = internalStateFlow.replayCache.map { value }

    // Compare and set the value atomically
    override fun compareAndSet(expect: T, update: T): Boolean {
        return internalStateFlow.compareAndSet(mapOf(key to expect), mapOf(key to update))
    }
}

