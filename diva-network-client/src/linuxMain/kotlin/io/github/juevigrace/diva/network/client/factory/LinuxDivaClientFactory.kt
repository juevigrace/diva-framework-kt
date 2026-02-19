package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.LinuxDivaClient
import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.github.juevigrace.diva.network.client.defaultConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.curl.CurlClientEngineConfig

class LinuxDivaClientFactory(
    engineFactory: HttpClientEngineFactory<CurlClientEngineConfig>,
    config: DivaClientConfig,
    httpClientConfig: HttpClientConfig<CurlClientEngineConfig>.() -> Unit = { defaultConfig(config) }
) : DivaClientFactoryBase<CurlClientEngineConfig>(engineFactory, config, httpClientConfig) {
    override fun create(): DivaClient {
        return LinuxDivaClient(engineFactory, config, httpClientConfig)
    }
}
