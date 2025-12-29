package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.WasmJsDivaClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.JsClientEngineConfig

class WasmJsDivaClientFactory(
    override val engineFactory: HttpClientEngineFactory<JsClientEngineConfig>,
    override val conf: HttpClientConfig<JsClientEngineConfig>.() -> Unit
) : DivaClientFactoryBase<JsClientEngineConfig>(engineFactory, conf) {
    override fun create(): DivaClient {
        return WasmJsDivaClient(engineFactory, conf)
    }
}
