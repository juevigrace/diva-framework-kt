package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.winhttp.WinHttpClientEngineConfig

internal class WindowsDivaClient(
    override val engineFactory: HttpClientEngineFactory<WinHttpClientEngineConfig>,
    override val config: DivaClientConfig,
    override val httpClientConfig: HttpClientConfig<WinHttpClientEngineConfig>.() -> Unit
) : DivaClientBase<WinHttpClientEngineConfig>(engineFactory, config, httpClientConfig)
