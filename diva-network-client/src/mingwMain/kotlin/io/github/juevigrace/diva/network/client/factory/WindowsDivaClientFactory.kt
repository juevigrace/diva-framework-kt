package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.WindowsDivaClient
import io.github.juevigrace.diva.network.client.defaultConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.winhttp.WinHttpClientEngineConfig

class WindowsDivaClientFactory(
    private val engineFactory: HttpClientEngineFactory<WinHttpClientEngineConfig>,
    private val httpClientConfig: HttpClientConfig<WinHttpClientEngineConfig>.() -> Unit = { defaultConfig() }
) : DivaClientFactory {
    override fun create(): DivaClient {
        return WindowsDivaClient(engineFactory, httpClientConfig)
    }
}
