package io.github.juevigrace.diva.network.client

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.winhttp.WinHttpClientEngineConfig

internal class WindowsNetworkClient(
    override val engineFactory: HttpClientEngineFactory<WinHttpClientEngineConfig>,
    override val conf: HttpClientConfig<WinHttpClientEngineConfig>.() -> Unit
) : NetworkClientBase<WinHttpClientEngineConfig>(engineFactory, conf)
