package io.github.juevigrace.diva.core.util

object AndroidDivaLogger : DivaLoggerBase() {
    override fun logToPlatform(tag: String, message: String, level: LogLevel) {
        when (level) {
            LogLevel.DEBUG -> android.util.Log.d(tag, message)
            LogLevel.INFO -> android.util.Log.i(tag, message)
            LogLevel.WARN -> android.util.Log.w(tag, message)
            LogLevel.ERROR -> android.util.Log.e(tag, message)
            LogLevel.FATAL -> android.util.Log.wtf(tag, message)
        }
    }
}

actual fun createDivaLogger(): DivaLogger {
    return AndroidDivaLogger
}