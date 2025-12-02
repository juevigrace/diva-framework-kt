package io.github.juevigrace.diva.network.client

interface DivaClientFactory {
    fun create(): DivaClient
}
