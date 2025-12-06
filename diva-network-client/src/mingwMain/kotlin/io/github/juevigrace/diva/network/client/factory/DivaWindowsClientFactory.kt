package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.DivaWindowsClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.winhttp.WinHttpClientEngineConfig

class DivaWindowsClientFactory : DivaClientFactory<WinHttpClientEngineConfig> {
    override fun create(
        engineFactory: HttpClientEngineFactory<WinHttpClientEngineConfig>,
        conf: HttpClientConfig<WinHttpClientEngineConfig>.() -> Unit
    ): DivaClient {
        return DivaWindowsClient(engineFactory, conf)
    }
}
