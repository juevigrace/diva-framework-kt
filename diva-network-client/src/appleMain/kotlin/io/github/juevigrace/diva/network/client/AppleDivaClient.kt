package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.DarwinClientEngineConfig

internal class AppleDivaClient(
    engineFactory: HttpClientEngineFactory<DarwinClientEngineConfig>,
    config: DivaClientConfig,
    httpClientConfig: HttpClientConfig<DarwinClientEngineConfig>.() -> Unit = { defaultConfig(config) }
) : DivaClientBase<DarwinClientEngineConfig>(engineFactory, config, httpClientConfig)
