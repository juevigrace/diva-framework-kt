package io.github.juevigrace.diva.core.errors

import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.getOrElse
import io.github.juevigrace.diva.core.ifPresent

open class DivaNetworkException(
    message: String,
    override val cause: Throwable? = null
) : Exception(message, cause) {
    open val url: Option<String> = Option.None
    open val statusCode: Option<Int> = Option.None
    open val details: Option<String> = Option.None
}

class ConfigureClientException(
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaNetworkException("Failed to configure client.", cause)


class NetworkConnectionException(
    override val url: Option<String>,
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaNetworkException(
    buildMessage(url, "Connection failed", details),
    cause
)

class NetworkTimeoutException(
    override val url: Option<String>,
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaNetworkException(
    buildMessage(url, "Request timeout", details),
    cause
)

class HttpException(
    override val statusCode: Option<Int>,
    override val url: Option<String> = Option.None,
    val responseBody: Option<String> = Option.None,
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaNetworkException(
    buildMessage(url, "HTTP ${statusCode.getOrElse { "unknown" }}", details),
    cause
)

class SslException(
    override val details: Option<String>,
    cause: Throwable? = null
) : DivaNetworkException("SSL error: ${details.getOrElse { "unknown" }}", cause)

private fun buildMessage(url: Option<String>, prefix: String, details: Option<String>): String {
    return buildString {
        append(prefix)
        url.ifPresent { append(" for URL: $it") }
        details.ifPresent { append(" - $it") }
    }
}
