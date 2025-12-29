package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.WindowsDivaClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.winhttp.WinHttpClientEngineConfig

class WindowsDivaClientFactory(
    override val engineFactory: HttpClientEngineFactory<WinHttpClientEngineConfig>,
    override val conf: HttpClientConfig<WinHttpClientEngineConfig>.() -> Unit
) : DivaClientFactoryBase<WinHttpClientEngineConfig>(engineFactory, conf) {
    override fun create(): DivaClient {
        return WindowsDivaClient(engineFactory, conf)
    }
}
