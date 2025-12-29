package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.AndroidDivaClient
import io.github.juevigrace.diva.network.client.DivaClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttpConfig

class AndroidDivaClientFactory(
    override val engineFactory: HttpClientEngineFactory<OkHttpConfig>,
    override val conf: HttpClientConfig<OkHttpConfig>.() -> Unit
) : DivaClientFactoryBase<OkHttpConfig>(engineFactory, conf) {
    override fun create(): DivaClient {
        return AndroidDivaClient(engineFactory, conf)
    }
}
