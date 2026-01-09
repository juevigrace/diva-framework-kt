package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.JsClientEngineConfig

internal class JsDivaClient(
    override val engineFactory: HttpClientEngineFactory<JsClientEngineConfig>,
    override val config: DivaClientConfig,
    override val httpClientConfig: HttpClientConfig<JsClientEngineConfig>.() -> Unit
) : DivaClientBase<JsClientEngineConfig>(engineFactory, config, httpClientConfig)
