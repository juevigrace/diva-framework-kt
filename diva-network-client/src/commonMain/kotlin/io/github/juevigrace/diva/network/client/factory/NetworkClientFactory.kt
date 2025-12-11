package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.NetworkClient

interface NetworkClientFactory {
    fun create(): NetworkClient
}
