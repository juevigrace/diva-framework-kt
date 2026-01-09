package io.github.juevigrace.diva.network.client.config

import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import kotlin.time.Duration
import kotlin.time.DurationUnit

data class DivaClientConfig(
    val baseUrl: String,
    val logger: Logger = Logger.DEFAULT,
    val logLevel: LogLevel = LogLevel.INFO,
    val requestTimeout: Long = Duration.parse("30").toLong(DurationUnit.SECONDS),
    val connectTimeout: Long = Duration.parse("10").toLong(DurationUnit.SECONDS),
    val socketTimeout: Long = Duration.parse("10").toLong(DurationUnit.SECONDS),
)
