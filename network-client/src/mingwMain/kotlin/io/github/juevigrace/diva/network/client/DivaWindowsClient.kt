package io.github.juevigrace.diva.network.client

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.winhttp.WinHttpClientEngineConfig

internal class DivaWindowsClient(
    override val engineFactory: HttpClientEngineFactory<WinHttpClientEngineConfig>,
    override val conf: HttpClientConfig<WinHttpClientEngineConfig>.() -> Unit
) : DivaClientBase<WinHttpClientEngineConfig>(engineFactory, conf)
