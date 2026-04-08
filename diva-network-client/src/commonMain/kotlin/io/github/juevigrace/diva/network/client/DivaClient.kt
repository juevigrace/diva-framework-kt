package io.github.juevigrace.diva.network.client

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.sse.ClientSSESession
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import kotlinx.serialization.KSerializer
import kotlin.time.Duration

interface DivaClient {
    fun config(config: HttpClientConfig<*>.() -> Unit): Result<Unit>

    suspend fun sse(
        path: String,
        queryParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap(),
        block: suspend ClientSSESession.() -> Unit
    ): Result<Unit>

    suspend fun webSocket(
        path: String,
        queryParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap(),
        block: suspend DefaultClientWebSocketSession.() -> Unit
    ): Result<Unit>

    suspend fun get(
        path: String,
        queryParams: Map<String, String> = emptyMap(),
        headers: Map<String, String> = emptyMap(),
        contentType: ContentType = ContentType.Application.Json,
    ): Result<HttpResponse>

    suspend fun<T> post(
        path: String,
        body: T,
        headers: Map<String, String> = emptyMap(),
        contentType: ContentType = ContentType.Application.Json,
        serializer: KSerializer<T>,
    ): Result<HttpResponse>

    suspend fun post(
        path: String,
        headers: Map<String, String> = emptyMap(),
        contentType: ContentType = ContentType.Application.Json,
    ): Result<HttpResponse>

    suspend fun<T> put(
        path: String,
        body: T,
        headers: Map<String, String> = emptyMap(),
        contentType: ContentType = ContentType.Application.Json,
        serializer: KSerializer<T>,
    ): Result<HttpResponse>

    suspend fun<T> patch(
        path: String,
        body: T,
        headers: Map<String, String> = emptyMap(),
        contentType: ContentType = ContentType.Application.Json,
        serializer: KSerializer<T>,
    ): Result<HttpResponse>

    suspend fun delete(
        path: String,
        headers: Map<String, String> = emptyMap(),
        contentType: ContentType = ContentType.Application.Json,
    ): Result<HttpResponse>

    suspend fun<T> delete(
        path: String,
        body: T,
        headers: Map<String, String> = emptyMap(),
        contentType: ContentType = ContentType.Application.Json,
        serializer: KSerializer<T>,
    ): Result<HttpResponse>

    suspend fun call(
        method: HttpMethod,
        path: String,
        headers: Map<String, String>,
        contentType: ContentType,
    ): Result<HttpResponse>

    suspend fun <T> call(
        method: HttpMethod,
        path: String,
        body: T,
        headers: Map<String, String>,
        contentType: ContentType,
        serializer: KSerializer<T>,
    ): Result<HttpResponse>

    companion object {
        val DEFAULT_LOGGER: Logger = Logger.DEFAULT
        val DEFAULT_LOG_LEVEL: LogLevel = LogLevel.INFO
        val DEFAULT_REQUEST_TIMEOUT: Long = Duration.parse("30s").inWholeMilliseconds
        val DEFAULT_CONNECT_TIMEOUT: Long = Duration.parse("10s").inWholeMilliseconds
        val DEFAULT_SOCKET_TIMEOUT: Long = Duration.parse("10s").inWholeMilliseconds
    }
}
