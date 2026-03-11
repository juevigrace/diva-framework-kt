package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.AppleDivaClient
import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.defaultConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.DarwinClientEngineConfig

class AppleDivaClientFactory(
    private val engineFactory: HttpClientEngineFactory<DarwinClientEngineConfig>,
    private val httpClientConfig: HttpClientConfig<DarwinClientEngineConfig>.() -> Unit = { defaultConfig() }
) : DivaClientFactory {
    override fun create(): DivaClient {
        return AppleDivaClient(engineFactory, httpClientConfig)
    }
}
