package io.github.juevigrace.diva.network.client

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttpConfig

internal class JvmDivaClient(
    engineFactory: HttpClientEngineFactory<OkHttpConfig>,
    httpClientConfig: HttpClientConfig<OkHttpConfig>.() -> Unit = { defaultConfig() }
) : DivaClientBase<OkHttpConfig>(engineFactory, httpClientConfig)
