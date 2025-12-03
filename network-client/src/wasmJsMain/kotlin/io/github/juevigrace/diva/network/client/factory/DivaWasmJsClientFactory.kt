package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.DivaWasmJsClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.JsClientEngineConfig

class DivaWasmJsClientFactory : DivaClientFactory<JsClientEngineConfig> {
    override fun create(
        engineFactory: HttpClientEngineFactory<JsClientEngineConfig>,
        conf: HttpClientConfig<JsClientEngineConfig>.() -> Unit
    ): DivaClient {
        return DivaWasmJsClient(engineFactory, conf)
    }
}
