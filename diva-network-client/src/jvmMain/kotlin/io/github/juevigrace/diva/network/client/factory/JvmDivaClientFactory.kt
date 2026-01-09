package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.JvmDivaClient
import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.github.juevigrace.diva.network.client.defaultConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttpConfig

class JvmDivaClientFactory(
    override val engineFactory: HttpClientEngineFactory<OkHttpConfig>,
    override val config: DivaClientConfig,
    override val httpClientConfig: HttpClientConfig<OkHttpConfig>.() -> Unit = { defaultConfig(config) }
) : DivaClientFactoryBase<OkHttpConfig>(engineFactory, config, httpClientConfig) {
    override fun create(): DivaClient {
        return JvmDivaClient(engineFactory, config, httpClientConfig)
    }
}
