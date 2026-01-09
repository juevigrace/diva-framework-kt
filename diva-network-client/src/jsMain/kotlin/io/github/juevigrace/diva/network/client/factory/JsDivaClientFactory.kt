package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.JsDivaClient
import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.github.juevigrace.diva.network.client.defaultConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.JsClientEngineConfig

class JsDivaClientFactory(
    override val engineFactory: HttpClientEngineFactory<JsClientEngineConfig>,
    override val config: DivaClientConfig,
    override val httpClientConfig: HttpClientConfig<JsClientEngineConfig>.() -> Unit = { defaultConfig(config) }
) : DivaClientFactoryBase<JsClientEngineConfig>(engineFactory, config, httpClientConfig) {
    override fun create(): DivaClient {
        return JsDivaClient(engineFactory, config, httpClientConfig)
    }
}
