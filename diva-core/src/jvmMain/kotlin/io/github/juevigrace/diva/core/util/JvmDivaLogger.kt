package io.github.juevigrace.diva.core.util

import org.slf4j.LoggerFactory

object JvmDivaLogger : DivaLoggerBase() {
    private val logger = LoggerFactory.getLogger("Diva")

    override fun logToPlatform(tag: String, message: String, level: LogLevel) {
        when (level) {
            LogLevel.DEBUG -> logger.debug(message)
            LogLevel.INFO -> logger.info(message)
            LogLevel.WARN -> logger.warn(message)
            LogLevel.ERROR -> logger.error(message)
            LogLevel.FATAL -> logger.error(message)
        }
    }
}

actual fun createDivaLogger(): DivaLogger {
    return JvmDivaLogger
}
