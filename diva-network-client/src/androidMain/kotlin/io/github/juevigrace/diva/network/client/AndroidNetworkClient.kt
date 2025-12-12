package io.github.juevigrace.diva.network.client

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttpConfig

internal class AndroidNetworkClient(
    override val engineFactory: HttpClientEngineFactory<OkHttpConfig>,
    override val conf: HttpClientConfig<OkHttpConfig>.() -> Unit
) : NetworkClientBase<OkHttpConfig>(engineFactory, conf)
