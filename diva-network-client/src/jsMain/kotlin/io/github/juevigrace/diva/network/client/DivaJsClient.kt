package io.github.juevigrace.diva.network.client

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.js.JsClientEngineConfig

internal class DivaJsClient(
    override val engineFactory: HttpClientEngineFactory<JsClientEngineConfig>,
    override val conf: HttpClientConfig<JsClientEngineConfig>.() -> Unit
) : DivaClientBase<JsClientEngineConfig>(engineFactory, conf)
