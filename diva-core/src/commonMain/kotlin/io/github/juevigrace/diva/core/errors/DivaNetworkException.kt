package io.github.juevigrace.diva.core.errors

import io.github.juevigrace.diva.core.Option

// Base
open class DivaNetworkException(
    message: String,
    override val cause: Throwable? = null
) : Exception(message, cause) {
    open val url: Option<String> = Option.None
    open val statusCode: Option<Int> = Option.None
    open val details: Option<String> = Option.None
}
// Connection issues
class NetworkConnectionException(
    override val url: Option<String> = Option.None,
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaNetworkException("Connection failed", cause)
// Timeout
class NetworkTimeoutException(
    override val url: Option<String> = Option.None,
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaNetworkException("Request timeout", cause)
// HTTP errors (4xx, 5xx)
class HttpException(
    override val statusCode: Int,
    override val url: Option<String> = Option.None,
    val responseBody: Option<String> = Option.None,
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaNetworkException("HTTP $statusCode", cause)
// SSL/TLS errors
class SslException(
    override val details: Option<String>,
    cause: Throwable? = null
) : DivaNetworkException("SSL error", cause)
