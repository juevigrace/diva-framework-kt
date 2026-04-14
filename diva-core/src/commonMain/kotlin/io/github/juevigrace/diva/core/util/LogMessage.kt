package io.github.juevigrace.diva.core.util

import kotlin.time.Clock

data class LogMessage(
    val tag: String,
    val message: String,
    val level: LogLevel,
    val timestamp: Long = Clock.System.now().toEpochMilliseconds()
)
