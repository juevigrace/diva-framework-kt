package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttpConfig

internal class AndroidDivaClient(
    engineFactory: HttpClientEngineFactory<OkHttpConfig>,
    config: DivaClientConfig,
    httpClientConfig: HttpClientConfig<OkHttpConfig>.() -> Unit = { defaultConfig(config) }
) : DivaClientBase<OkHttpConfig>(engineFactory, config, httpClientConfig)
