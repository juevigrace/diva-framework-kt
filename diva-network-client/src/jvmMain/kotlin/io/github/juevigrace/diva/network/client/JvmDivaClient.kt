package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.network.client.config.DivaClientConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttpConfig

internal class JvmDivaClient(
    override val engineFactory: HttpClientEngineFactory<OkHttpConfig>,
    override val config: DivaClientConfig,
    override val httpClientConfig: HttpClientConfig<OkHttpConfig>.() -> Unit
) : DivaClientBase<OkHttpConfig>(engineFactory, config, httpClientConfig)
