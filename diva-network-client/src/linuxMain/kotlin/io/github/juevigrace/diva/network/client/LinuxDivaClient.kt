package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.curl.CurlClientEngineConfig

internal class LinuxDivaClient(
    override val engineFactory: HttpClientEngineFactory<CurlClientEngineConfig>,
    override val config: DivaClientConfig,
    override val httpClientConfig: HttpClientConfig<CurlClientEngineConfig>.() -> Unit
) : DivaClientBase<CurlClientEngineConfig>(engineFactory, config, httpClientConfig)
