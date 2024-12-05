package com.billy.simpleunitconvert.core.data.utils

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

object AppLogger {
    private const val TAG = "AppLogger"

    fun log(
        message: String,
        tag: String = TAG,
        priority: Int = Log.DEBUG,
        throwable: Throwable? = null
    ) {
        when (priority) {
            Log.DEBUG -> Log.d(tag, message, throwable)
            Log.INFO -> Log.i(tag, message, throwable)
            Log.WARN -> Log.w(tag, message, throwable)
            Log.ERROR -> Log.e(tag, message, throwable)
            else -> Log.v(tag, message, throwable)
        }

        // Optionally log to Firebase or analytics here
        FirebaseCrashlytics.getInstance().log("$tag: $message")
    }
}

fun Any.logError(message: String) {
    AppLogger.log(message, tag = this::class.java.simpleName, priority = Log.ERROR)
}

fun Any.logInfo(message: String) {
    AppLogger.log(message, tag = this::class.java.simpleName, priority = Log.INFO)
}
