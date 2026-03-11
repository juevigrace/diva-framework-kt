package io.github.juevigrace.diva.network.client

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.JsClientEngineConfig

internal class JsDivaClient(
    engineFactory: HttpClientEngineFactory<JsClientEngineConfig>,
    httpClientConfig: HttpClientConfig<JsClientEngineConfig>.() -> Unit = { defaultConfig() }
) : DivaClientBase<JsClientEngineConfig>(engineFactory, httpClientConfig)
