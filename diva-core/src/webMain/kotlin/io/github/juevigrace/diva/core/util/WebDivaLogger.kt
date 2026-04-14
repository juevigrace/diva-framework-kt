package io.github.juevigrace.diva.core.util

object WebDivaLogger : DivaLoggerBase() {
    override fun logToPlatform(tag: String, message: String, level: LogLevel) {
        val output = "[$tag] $message"
        when (level) {
            LogLevel.DEBUG -> console.log(output)
            LogLevel.INFO -> console.log(output)
            LogLevel.WARN -> console.warn(output)
            LogLevel.ERROR -> console.error(output)
            LogLevel.FATAL -> console.error(output)
        }
    }
}

actual fun createDivaLogger(): DivaLogger {
    return WebDivaLogger
}