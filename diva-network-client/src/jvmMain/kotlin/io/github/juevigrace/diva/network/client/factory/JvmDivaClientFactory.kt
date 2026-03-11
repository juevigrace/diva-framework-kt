package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient
import io.github.juevigrace.diva.network.client.JvmDivaClient
import io.github.juevigrace.diva.network.client.defaultConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttpConfig

class JvmDivaClientFactory(
    private val engineFactory: HttpClientEngineFactory<OkHttpConfig>,
    private val httpClientConfig: HttpClientConfig<OkHttpConfig>.() -> Unit = { defaultConfig() }
) : DivaClientFactory {
    override fun create(): DivaClient {
        return JvmDivaClient(engineFactory, httpClientConfig)
    }
}
