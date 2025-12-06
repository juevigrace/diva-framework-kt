package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaAppleClient
import io.github.juevigrace.diva.network.client.DivaClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.DarwinClientEngineConfig

class DivaAppleClientFactory : DivaClientFactory<DarwinClientEngineConfig> {
    override fun create(
        engineFactory: HttpClientEngineFactory<DarwinClientEngineConfig>,
        conf: HttpClientConfig<DarwinClientEngineConfig>.() -> Unit
    ): DivaClient {
        return DivaAppleClient(engineFactory, conf)
    }
}
