package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.DivaJvmClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttpConfig

class DivaJvmClientFactory : DivaClientFactory<OkHttpConfig> {
    override fun create(
        engineFactory: HttpClientEngineFactory<OkHttpConfig>,
        conf: HttpClientConfig<OkHttpConfig>.() -> Unit
    ): DivaClient {
        return DivaJvmClient(engineFactory, conf)
    }
}
