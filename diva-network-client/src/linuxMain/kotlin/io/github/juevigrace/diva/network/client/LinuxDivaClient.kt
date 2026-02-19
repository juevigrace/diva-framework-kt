package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.curl.CurlClientEngineConfig

internal class LinuxDivaClient(
    engineFactory: HttpClientEngineFactory<CurlClientEngineConfig>,
    config: DivaClientConfig,
    httpClientConfig: HttpClientConfig<CurlClientEngineConfig>.() -> Unit = { defaultConfig(config) }
) : DivaClientBase<CurlClientEngineConfig>(engineFactory, config, httpClientConfig)
