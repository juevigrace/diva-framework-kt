package io.github.juevigrace.diva.network.client

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.DarwinClientEngineConfig

internal class NetworkAppleClient(
    override val engineFactory: HttpClientEngineFactory<DarwinClientEngineConfig>,
    override val conf: HttpClientConfig<DarwinClientEngineConfig>.() -> Unit
) : NetworkClientBase<DarwinClientEngineConfig>(engineFactory, conf)
