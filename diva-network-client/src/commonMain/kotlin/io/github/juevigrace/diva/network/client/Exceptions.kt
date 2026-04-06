package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaNetworkException
import io.github.juevigrace.diva.core.errors.HttpException
import io.github.juevigrace.diva.core.errors.NetworkTimeoutException
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException

fun Throwable.toDivaNetworkException(
    url: Option<String> = Option.None,
    details: Option<String> = Option.None
): DivaNetworkException = when (this) {
    is HttpRequestTimeoutException -> NetworkTimeoutException(url, details, this)
    is ConnectTimeoutException -> NetworkTimeoutException(url, details, this)
    is ClientRequestException -> HttpException(
        Option.Some(response.status.value),
        url,
        Option.None,
        details,
        this
    )
    is ServerResponseException -> HttpException(
        Option.Some(response.status.value),
        url,
        Option.None,
        details,
        this
    )
    else -> DivaNetworkException(message ?: "Unknown error", this)
}
