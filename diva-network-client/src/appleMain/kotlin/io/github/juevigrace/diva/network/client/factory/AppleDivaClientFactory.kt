package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.AppleDivaClient
import io.github.juevigrace.diva.network.client.DivaClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.DarwinClientEngineConfig

class AppleDivaClientFactory(
    override val engineFactory: HttpClientEngineFactory<DarwinClientEngineConfig>,
    override val conf: HttpClientConfig<DarwinClientEngineConfig>.() -> Unit
) : DivaClientFactoryBase<DarwinClientEngineConfig>(engineFactory, conf) {
    override fun create(): DivaClient {
        return AppleDivaClient(engineFactory, conf)
    }
}
