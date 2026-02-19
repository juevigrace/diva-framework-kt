package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.winhttp.WinHttpClientEngineConfig

internal class WindowsDivaClient(
    engineFactory: HttpClientEngineFactory<WinHttpClientEngineConfig>,
    config: DivaClientConfig,
    httpClientConfig: HttpClientConfig<WinHttpClientEngineConfig>.() -> Unit = { defaultConfig(config) }
) : DivaClientBase<WinHttpClientEngineConfig>(engineFactory, config, httpClientConfig)
