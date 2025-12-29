package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.LinuxDivaClient
import io.github.juevigrace.diva.network.client.DivaClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.curl.CurlClientEngineConfig

class LinuxDivaClientFactory(
    override val engineFactory: HttpClientEngineFactory<CurlClientEngineConfig>,
    override val conf: HttpClientConfig<CurlClientEngineConfig>.() -> Unit,
) : DivaClientFactoryBase<CurlClientEngineConfig>(engineFactory, conf) {
    override fun create(): DivaClient {
        return LinuxDivaClient(engineFactory, conf)
    }
}
