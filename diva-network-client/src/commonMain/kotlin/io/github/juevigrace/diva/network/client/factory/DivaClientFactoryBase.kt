package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

abstract class DivaClientFactoryBase<C : HttpClientEngineConfig>(
    protected open val engineFactory: HttpClientEngineFactory<C>,
    protected open val config: DivaClientConfig,
    protected open val httpClientConfig: HttpClientConfig<C>.() -> Unit
) : DivaClientFactory
