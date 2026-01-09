package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.AppleDivaClient
import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.github.juevigrace.diva.network.client.defaultConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.DarwinClientEngineConfig

class AppleDivaClientFactory(
    override val engineFactory: HttpClientEngineFactory<DarwinClientEngineConfig>,
    override val config: DivaClientConfig,
    override val httpClientConfig: HttpClientConfig<DarwinClientEngineConfig>.() -> Unit = { defaultConfig(config) }
) : DivaClientFactoryBase<DarwinClientEngineConfig>(engineFactory, config, httpClientConfig) {
    override fun create(): DivaClient {
        return AppleDivaClient(engineFactory, config, httpClientConfig)
    }
}
