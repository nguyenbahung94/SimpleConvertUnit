package com.billy.simpleunitconvert.feature.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

/**
 * Checks if any network is available
 */
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // For Android M and above
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        networkCapabilities.run {
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        }
    }
    // For Android versions below M
    else {
        @Suppress("DEPRECATION")
        val networkInfo = connectivityManager.activeNetworkInfo
        @Suppress("DEPRECATION")
        networkInfo != null && networkInfo.isConnected
    }
}

/**
 * Checks if WiFi is connected
 */
fun Context.isWifiConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    } else {
        @Suppress("DEPRECATION")
        val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        @Suppress("DEPRECATION")
        networkInfo?.isConnected == true
    }
}

/**
 * Checks if Cellular network is connected
 */
fun Context.isCellularConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
    } else {
        @Suppress("DEPRECATION")
        val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        @Suppress("DEPRECATION")
        networkInfo?.isConnected == true
    }
}

/**
 * Checks if Ethernet is connected
 */
fun Context.isEthernetConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true
    } else {
        @Suppress("DEPRECATION")
        val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET)
        @Suppress("DEPRECATION")
        networkInfo?.isConnected == true
    }
}

/**
 * Returns the current network type as a string
 */
fun Context.getNetworkType(): String {
    return when {
        isWifiConnected() -> "WiFi"
        isCellularConnected() -> "Cellular"
        isEthernetConnected() -> "Ethernet"
        else -> "No Network"
    }
}
