package com.billy.simpleunitconvert.core.navigation.type

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class CustomNavType<T : Parcelable>(
    private val clazz: Class<T>,
    private val serializer: KSerializer<T>,
) : NavType<T?>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): T? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, clazz)
        } else {
            @Suppress("DEPRECATION") // for backwards compatibility
            (bundle.getParcelable(key)) as? T
        }

    override fun put(bundle: Bundle, key: String, value: T?) =
        bundle.putParcelable(key, value)

    override fun parseValue(value: String): T? =
        if (value == "null") { // Explicit handling for "null" string
            null
        } else {
            Json.decodeFromString(serializer, value)
        }

    override fun serializeAsValue(value: T?): String {
        return if (value == null) {
            "null" // Encode null values explicitly as "null"
        } else {
            Json.encodeToString(serializer, value)
        }
    }

    override val name: String = clazz.name
}


inline fun <reified T> serializableNavType(isNullableAllowed: Boolean = false) =
    object : NavType<T>(isNullableAllowed = isNullableAllowed) {
        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putString(key, serializeAsValue(value))
        }

        override fun get(bundle: Bundle, key: String): T? {
            return bundle.getString(key)?.let { parseValue(it) }
        }

        override fun serializeAsValue(value: T): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun parseValue(value: String): T {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is NavType<*>) return false
            if (other::class.java != this::class.java) return false
            if (isNullableAllowed != other.isNullableAllowed) return false
            return true
        }
    }

inline fun <reified T> typeMapOf(): Pair<KType, NavType<T>> {
    val type = typeOf<T>()
    return type to serializableNavType<T>(isNullableAllowed = type.isMarkedNullable)
}
