package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.JsClientEngineConfig

internal class JsDivaClient(
    engineFactory: HttpClientEngineFactory<JsClientEngineConfig>,
    config: DivaClientConfig,
    httpClientConfig: HttpClientConfig<JsClientEngineConfig>.() -> Unit = { defaultConfig(config) }
) : DivaClientBase<JsClientEngineConfig>(engineFactory, config, httpClientConfig)
