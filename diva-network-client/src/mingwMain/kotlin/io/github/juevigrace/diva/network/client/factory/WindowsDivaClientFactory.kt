package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.WindowsDivaClient
import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.github.juevigrace.diva.network.client.defaultConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.winhttp.WinHttpClientEngineConfig

class WindowsDivaClientFactory(
    engineFactory: HttpClientEngineFactory<WinHttpClientEngineConfig>,
    config: DivaClientConfig,
    httpClientConfig: HttpClientConfig<WinHttpClientEngineConfig>.() -> Unit = { defaultConfig(config) }
) : DivaClientFactoryBase<WinHttpClientEngineConfig>(engineFactory, config, httpClientConfig) {
    override fun create(): DivaClient {
        return WindowsDivaClient(engineFactory, config, httpClientConfig)
    }
}
