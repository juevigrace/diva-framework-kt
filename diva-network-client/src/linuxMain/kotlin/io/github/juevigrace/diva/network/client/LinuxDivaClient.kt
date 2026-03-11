package io.github.juevigrace.diva.network.client

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.curl.CurlClientEngineConfig

internal class LinuxDivaClient(
    engineFactory: HttpClientEngineFactory<CurlClientEngineConfig>,
    httpClientConfig: HttpClientConfig<CurlClientEngineConfig>.() -> Unit = { defaultConfig() }
) : DivaClientBase<CurlClientEngineConfig>(engineFactory, httpClientConfig)
