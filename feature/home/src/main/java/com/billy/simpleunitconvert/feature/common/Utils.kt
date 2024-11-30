package com.billy.simpleunitconvert.feature.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    fun getCurrentDateFormat(): String {
      return  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).format(Date())
    }
}
