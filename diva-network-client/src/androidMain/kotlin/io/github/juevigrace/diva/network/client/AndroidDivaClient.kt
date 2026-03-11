package io.github.juevigrace.diva.network.client

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttpConfig

internal class AndroidDivaClient(
    engineFactory: HttpClientEngineFactory<OkHttpConfig>,
    httpClientConfig: HttpClientConfig<OkHttpConfig>.() -> Unit = { defaultConfig() }
) : DivaClientBase<OkHttpConfig>(engineFactory, httpClientConfig)
