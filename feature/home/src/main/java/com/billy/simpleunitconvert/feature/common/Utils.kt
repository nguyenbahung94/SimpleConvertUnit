package com.billy.simpleunitconvert.feature.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    var isEnableAds = false
    var isJustShowOpenApp = false
    private var maxCount = 2
    private var currentCount = 0

    fun incrementCount() {
        currentCount++
    }

    fun isTimeVisitEnough(): Boolean {
        return currentCount >= maxCount
    }
    fun resetCountAndIncreaseMaxCount() {
        currentCount = 0
         if (maxCount == 2) {
            maxCount = 5
        } else {
            maxCount++
        }
    }

    fun getCurrentDateFormat(): String {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).format(Date())
    }

    object ADSUNITID {

        //this for testing
        const val BANNER = "ca-app-pub-3940256099942544/6300978111"
        const val INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712"
        const val REWARDED_VIDEO = "ca-app-pub-3940256099942544/5224354917"
        const val APP_OPEN = "ca-app-pub-3940256099942544/9257395921"

//        const val BANNER = "ca-app-pub-7779103049102360/3606469483"
//        const val INTERSTITIAL = "ca-app-pub-7779103049102360/3462827812"
//        const val REWARDED_VIDEO = "ca-app-pub-7779103049102360/3336401970"
//        const val APP_OPEN = "ca-app-pub-7779103049102360/2073895963"
    }
}
