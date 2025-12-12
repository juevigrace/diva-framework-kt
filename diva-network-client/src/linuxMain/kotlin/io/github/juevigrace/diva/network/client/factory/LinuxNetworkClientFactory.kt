package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.LinuxNetworkClient
import io.github.juevigrace.diva.network.client.NetworkClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.curl.CurlClientEngineConfig

class LinuxNetworkClientFactory(
    override val engineFactory: HttpClientEngineFactory<CurlClientEngineConfig>,
    override val conf: HttpClientConfig<CurlClientEngineConfig>.() -> Unit,
) : NetworkClientFactoryBase<CurlClientEngineConfig>(engineFactory, conf) {
    override fun create(): NetworkClient {
        return LinuxNetworkClient(engineFactory, conf)
    }
}
