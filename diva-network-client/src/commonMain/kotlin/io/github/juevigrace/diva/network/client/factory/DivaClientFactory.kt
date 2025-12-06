package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

interface DivaClientFactory<C : HttpClientEngineConfig> {
    fun create(
        engineFactory: HttpClientEngineFactory<C>,
        conf: HttpClientConfig<C>.() -> Unit
    ): DivaClient
}
