package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.NetworkClient
import io.github.juevigrace.diva.network.client.WindowsNetworkClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.winhttp.WinHttpClientEngineConfig

class WindowsNetworkClientFactory(
    override val engineFactory: HttpClientEngineFactory<WinHttpClientEngineConfig>,
    override val conf: HttpClientConfig<WinHttpClientEngineConfig>.() -> Unit
) : NetworkClientFactoryBase<WinHttpClientEngineConfig>(engineFactory, conf) {
    override fun create(): NetworkClient {
        return WindowsNetworkClient(engineFactory, conf)
    }
}
