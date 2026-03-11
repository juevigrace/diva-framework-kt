package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.LinuxDivaClient
import io.github.juevigrace.diva.network.client.defaultConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.curl.CurlClientEngineConfig

class LinuxDivaClientFactory(
    private val engineFactory: HttpClientEngineFactory<CurlClientEngineConfig>,
    private val httpClientConfig: HttpClientConfig<CurlClientEngineConfig>.() -> Unit = { defaultConfig() }
) : DivaClientFactory {
    override fun create(): DivaClient {
        return LinuxDivaClient(engineFactory, httpClientConfig)
    }
}
