package io.github.juevigrace.diva.network.client.factory

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

abstract class NetworkClientFactoryBase<C : HttpClientEngineConfig>(
    protected open val engineFactory: HttpClientEngineFactory<C>,
    protected open val conf: HttpClientConfig<C>.() -> Unit
) : NetworkClientFactory
