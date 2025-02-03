package com.herdal.bitcointicker.core.util

import android.util.Log
import com.herdal.bitcointicker.BuildConfig

const val LOG_TAG = "bitcointicker_logger"

fun logD(message: String, tag: String = LOG_TAG) {
    log { Log.d(tag, message) }
}

fun logE(message: String, tag: String = LOG_TAG) {
    log { Log.e(tag, message) }
}

fun logI(message: String, tag: String = LOG_TAG) {
    log { Log.i(tag, message) }
}

fun logV(message: String, tag: String = LOG_TAG) {
    log { Log.v(tag, message) }
}

private inline fun log(func: () -> Int) {
    if (BuildConfig.DEBUG_MODE) {
        func()
    }
}