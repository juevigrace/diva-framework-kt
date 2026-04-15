package io.github.juevigrace.diva.core.util

import org.slf4j.LoggerFactory

object JvmDivaLogger : DivaLoggerBase() {
    private val logger = LoggerFactory.getLogger("Diva")

    override fun logToPlatform(tag: String, message: String, level: LogLevel) {
        when (level) {
            LogLevel.DEBUG -> logger.debug("[$tag] $message")
            LogLevel.INFO -> logger.info("[$tag] $message")
            LogLevel.WARN -> logger.warn("[$tag] $message")
            LogLevel.ERROR -> logger.error("[$tag] $message")
            LogLevel.FATAL -> logger.error("[$tag] $message")
        }
    }
}

actual fun createDivaLogger(): DivaLogger {
    return JvmDivaLogger
}
