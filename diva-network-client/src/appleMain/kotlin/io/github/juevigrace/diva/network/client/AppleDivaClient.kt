package io.github.juevigrace.diva.network.client

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.DarwinClientEngineConfig

internal class AppleDivaClient(
    engineFactory: HttpClientEngineFactory<DarwinClientEngineConfig>,
    httpClientConfig: HttpClientConfig<DarwinClientEngineConfig>.() -> Unit = { defaultConfig() }
) : DivaClientBase<DarwinClientEngineConfig>(engineFactory, httpClientConfig)
