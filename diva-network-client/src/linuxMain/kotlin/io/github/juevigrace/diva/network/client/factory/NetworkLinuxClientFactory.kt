package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.NetworkClient
import io.github.juevigrace.diva.network.client.NetworkLinuxClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.curl.CurlClientEngineConfig

class NetworkLinuxClientFactory(
    override val engineFactory: HttpClientEngineFactory<CurlClientEngineConfig>,
    override val conf: HttpClientConfig<CurlClientEngineConfig>.() -> Unit,
) : NetworkClientFactoryBase<CurlClientEngineConfig>(engineFactory, conf) {
    override fun create(): NetworkClient {
        return NetworkLinuxClient(engineFactory, conf)
    }
}
