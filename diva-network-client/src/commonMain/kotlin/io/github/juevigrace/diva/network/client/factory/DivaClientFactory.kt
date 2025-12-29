package io.github.juevigrace.diva.network.client.factory

import io.github.juevigrace.diva.network.client.DivaClient

interface DivaClientFactory {
    fun create(): DivaClient
}
