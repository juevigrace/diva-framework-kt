package io.github.juevigrace.diva.core.util

import android.util.Log

object AndroidDivaLogger : DivaLoggerBase() {
    override fun logToPlatform(tag: String, message: String, level: LogLevel) {
        when (level) {
            LogLevel.DEBUG -> Log.d(tag, message)
            LogLevel.INFO -> Log.i(tag, message)
            LogLevel.WARN -> Log.w(tag, message)
            LogLevel.ERROR -> Log.e(tag, message)
            LogLevel.FATAL -> Log.wtf(tag, message)
        }
    }
}

actual fun createDivaLogger(): DivaLogger {
    return AndroidDivaLogger
}