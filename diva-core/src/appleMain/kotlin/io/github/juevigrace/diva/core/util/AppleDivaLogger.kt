package io.github.juevigrace.diva.core.util

object AppleDivaLogger : DivaLoggerBase() {
    override fun logToPlatform(tag: String, message: String, level: LogLevel) {
        println("[$tag] [${level.name}] $message")
    }
}

actual fun createDivaLogger(): DivaLogger {
    return AppleDivaLogger
}