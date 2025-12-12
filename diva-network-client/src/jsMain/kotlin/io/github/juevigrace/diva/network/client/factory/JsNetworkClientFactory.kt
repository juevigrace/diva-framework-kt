package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.JsNetworkClient
import io.github.juevigrace.diva.network.client.NetworkClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.JsClientEngineConfig

class JsNetworkClientFactory(
    override val engineFactory: HttpClientEngineFactory<JsClientEngineConfig>,
    override val conf: HttpClientConfig<JsClientEngineConfig>.() -> Unit
) : NetworkClientFactoryBase<JsClientEngineConfig>(engineFactory, conf) {
    override fun create(): NetworkClient {
        return JsNetworkClient(engineFactory, conf)
    }
}
