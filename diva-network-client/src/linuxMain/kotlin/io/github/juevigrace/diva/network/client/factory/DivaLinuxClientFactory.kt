package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.DivaLinuxClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.curl.CurlClientEngineConfig

class DivaLinuxClientFactory : DivaClientFactory<CurlClientEngineConfig> {
    override fun create(
        engineFactory: HttpClientEngineFactory<CurlClientEngineConfig>,
        conf: HttpClientConfig<CurlClientEngineConfig>.() -> Unit
    ): DivaClient {
        return DivaLinuxClient(engineFactory, conf)
    }
}
