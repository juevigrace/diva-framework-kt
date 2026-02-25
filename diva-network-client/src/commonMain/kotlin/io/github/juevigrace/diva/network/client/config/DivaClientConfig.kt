package io.github.juevigrace.diva.network.client.config

import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import kotlin.time.Duration

data class DivaClientConfig(
    val baseUrl: String,
    val logger: Logger = Logger.DEFAULT,
    val logLevel: LogLevel = LogLevel.INFO,
    val requestTimeout: Long = Duration.parse("30s").inWholeMilliseconds,
    val connectTimeout: Long = Duration.parse("10s").inWholeMilliseconds,
    val socketTimeout: Long = Duration.parse("10s").inWholeMilliseconds,
)
