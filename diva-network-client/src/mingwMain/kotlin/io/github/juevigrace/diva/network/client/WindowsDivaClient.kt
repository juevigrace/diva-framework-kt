package io.github.juevigrace.diva.network.client

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.winhttp.WinHttpClientEngineConfig

internal class WindowsDivaClient(
    engineFactory: HttpClientEngineFactory<WinHttpClientEngineConfig>,
    httpClientConfig: HttpClientConfig<WinHttpClientEngineConfig>.() -> Unit = { defaultConfig() }
) : DivaClientBase<WinHttpClientEngineConfig>(engineFactory, httpClientConfig)
