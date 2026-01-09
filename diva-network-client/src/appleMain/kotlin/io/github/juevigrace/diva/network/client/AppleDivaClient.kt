package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.DarwinClientEngineConfig

internal class AppleDivaClient(
    override val engineFactory: HttpClientEngineFactory<DarwinClientEngineConfig>,
    override val config: DivaClientConfig,
    override val httpClientConfig: HttpClientConfig<DarwinClientEngineConfig>.() -> Unit
) : DivaClientBase<DarwinClientEngineConfig>(engineFactory, config, httpClientConfig)
