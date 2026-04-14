package io.github.juevigrace.diva.core.util

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface DivaLogger {
    val logs: Flow<LogMessage>
    var level: LogLevel

    fun log(tag: String = TAG, message: String, level: LogLevel = LogLevel.INFO)

    companion object {
        const val TAG = "DivaLogger"
    }
}

fun log(tag: String = DivaLogger.TAG, message: String, level: LogLevel = LogLevel.INFO) {
    createDivaLogger().log(tag, message, level)
}

fun logDebug(tag: String = DivaLogger.TAG, message: String) {
    log(tag, message, LogLevel.DEBUG)
}

fun logInfo(tag: String = DivaLogger.TAG, message: String) {
    log(tag, message, LogLevel.INFO)
}

fun logWarn(tag: String = DivaLogger.TAG, message: String) {
    log(tag, message, LogLevel.WARN)
}

fun logError(tag: String = DivaLogger.TAG, message: String) {
    log(tag, message, LogLevel.ERROR)
}

fun logFatal(tag: String = DivaLogger.TAG, message: String) {
    log(tag, message, LogLevel.FATAL)
}

abstract class DivaLoggerBase : DivaLogger {
    protected val logsCh: Channel<LogMessage> = Channel(Channel.CONFLATED)
    override val logs: Flow<LogMessage> = logsCh.receiveAsFlow()

    override var level: LogLevel = LogLevel.WARN

    override fun log(tag: String, message: String, level: LogLevel) {
        val shouldLog = level == LogLevel.ERROR || level == LogLevel.FATAL || level.ordinal >= this.level.ordinal
        if (!shouldLog) return

        logsCh.trySend(LogMessage(tag, message, level))
        logToPlatform(tag, message, level)
    }

    protected abstract fun logToPlatform(tag: String, message: String, level: LogLevel)
}

expect fun createDivaLogger(): DivaLogger
