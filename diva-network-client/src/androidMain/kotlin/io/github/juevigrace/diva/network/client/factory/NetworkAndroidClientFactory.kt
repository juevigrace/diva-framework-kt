package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.NetworkAndroidClient
import io.github.juevigrace.diva.network.client.NetworkClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttpConfig

class NetworkAndroidClientFactory(
    override val engineFactory: HttpClientEngineFactory<OkHttpConfig>,
    override val conf: HttpClientConfig<OkHttpConfig>.() -> Unit
) : NetworkClientFactoryBase<OkHttpConfig>(engineFactory, conf) {
    override fun create(): NetworkClient {
        return NetworkAndroidClient(engineFactory, conf)
    }
}
