package com.billy.simpleunitconvert.core.viewmodel

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel : ViewModel() {

    protected val key: ViewModelKey = ViewModelKey(this::class.java.name)

    protected fun <T> BaseViewModel.viewModelStateFlow(value: T): ViewModelStateFlow<T> {
        return ViewModelStateFlow(key = key, value = value)
    }
}

@Stable
data class UiState<T>(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean? = null,
    val data: T,
)

object StateExtensions {

    // Combined state updates
    fun <T> ViewModelStateFlow<UiState<T>>.updateState(
        loading: Boolean? = null,
        error: String? = null,
        success: Boolean? = null,
        data: T? = null,
    ) = update { state ->
        state.copy(
            isLoading = loading ?: state.isLoading,
            error = error ?: state.error,
            success = success ?: state.success,
            data = data ?: state.data
        )
    }

    // Utility function to update the state
    inline fun <T> MutableStateFlow<T>.update(updateFunction: (T) -> T) {
        this.value = updateFunction(this.value)
    }

    private fun <T> updateField(field: T, updateFunction: ((T) -> T)?): T =
        updateFunction?.let { it(field) } ?: field

    fun <T> ViewModelStateFlow<UiState<T>>.updateLoading(
        isLoading: Boolean,
    ) = update { state ->
        state.copy(isLoading = isLoading)
    }

    fun <T> ViewModelStateFlow<UiState<T>>.updateError(
        error: String?,
    ) = update { state ->
        state.copy(error = error)
    }
    fun <T> ViewModelStateFlow<UiState<T>>.updateSuccess(
        success: Boolean?,
    ) = update { state ->
        state.copy(success = success)
    }
}

fun <T> T.applyIf(condition: Boolean, block: T.() -> Unit): T {
    if (condition) block()
    return this
}

fun <T> T.letIf(condition: Boolean, block: (T) -> Unit): T {
    if (condition) block(this)
    return this
}

fun <T> T?.getOrDefault(default: T): T = this ?: default

fun Any.log(message: String) = Log.d(this::class.java.simpleName, message)



