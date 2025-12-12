package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.AppleNetworkClient
import io.github.juevigrace.diva.network.client.NetworkClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.DarwinClientEngineConfig

class AppleNetworkClientFactory(
    override val engineFactory: HttpClientEngineFactory<DarwinClientEngineConfig>,
    override val conf: HttpClientConfig<DarwinClientEngineConfig>.() -> Unit
) : NetworkClientFactoryBase<DarwinClientEngineConfig>(engineFactory, conf) {
    override fun create(): NetworkClient {
        return AppleNetworkClient(engineFactory, conf)
    }
}
