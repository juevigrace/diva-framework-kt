package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.AndroidDivaClient
import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.github.juevigrace.diva.network.client.defaultConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttpConfig

class AndroidDivaClientFactory(
    engineFactory: HttpClientEngineFactory<OkHttpConfig>,
    config: DivaClientConfig,
    httpClientConfig: HttpClientConfig<OkHttpConfig>.() -> Unit = { defaultConfig(config) }
) : DivaClientFactoryBase<OkHttpConfig>(engineFactory, config, httpClientConfig) {
    override fun create(): DivaClient {
        return AndroidDivaClient(engineFactory, config, httpClientConfig)
    }
}
