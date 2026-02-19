package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.github.juevigrace.diva.network.client.defaultConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

abstract class DivaClientFactoryBase<C : HttpClientEngineConfig>(
    protected val engineFactory: HttpClientEngineFactory<C>,
    protected val config: DivaClientConfig,
    protected val httpClientConfig: HttpClientConfig<C>.() -> Unit = { defaultConfig(config) }
) : DivaClientFactory
