package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.JsDivaClient
import io.github.juevigrace.diva.network.client.defaultConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.JsClientEngineConfig

class JsDivaClientFactory(
    private val engineFactory: HttpClientEngineFactory<JsClientEngineConfig>,
    private val httpClientConfig: HttpClientConfig<JsClientEngineConfig>.() -> Unit = { defaultConfig() }
) : DivaClientFactory {
    override fun create(): DivaClient {
        return JsDivaClient(engineFactory, httpClientConfig)
    }
}
