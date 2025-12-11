package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.NetworkClient
import io.github.juevigrace.diva.network.client.NetworkJsClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.JsClientEngineConfig

class NetworkJsClientFactory(
    override val engineFactory: HttpClientEngineFactory<JsClientEngineConfig>,
    override val conf: HttpClientConfig<JsClientEngineConfig>.() -> Unit
) : NetworkClientFactoryBase<JsClientEngineConfig>(engineFactory, conf) {
    override fun create(): NetworkClient {
        return NetworkJsClient(engineFactory, conf)
    }
}
