package io.github.juevigrace.diva.network.client

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.curl.CurlClientEngineConfig

internal class LinuxNetworkClient(
    override val engineFactory: HttpClientEngineFactory<CurlClientEngineConfig>,
    override val conf: HttpClientConfig<CurlClientEngineConfig>.() -> Unit
) : NetworkClientBase<CurlClientEngineConfig>(engineFactory, conf)
