package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.ConfigureDriverException
import io.github.juevigrace.diva.core.tryResult
import io.github.juevigrace.diva.core.util.logError
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.plugins.sse.ClientSSESession
import io.ktor.client.plugins.sse.sse
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.Json

abstract class DivaClientBase<C : HttpClientEngineConfig>(
    protected val engineFactory: HttpClientEngineFactory<C>,
    protected val httpClientConfig: HttpClientConfig<C>.() -> Unit = { defaultConfig() }
) : DivaClient {
    private var client: HttpClient = HttpClient(engineFactory, httpClientConfig)

    override fun config(config: HttpClientConfig<*>.() -> Unit): Result<Unit> {
        return tryResult(
            onError = { e ->
                val err = ConfigureDriverException(
                    details = Option.of("Failed to configure client"),
                    cause = e
                )
                logError(err::class.simpleName ?: "ConfigureDriverException", err.message ?: err.toString())
                err
            }
        ) {
            client = client.config(config)
        }
    }

    override suspend fun sse(
        path: String,
        queryParams: Map<String, String>,
        headers: Map<String, String>,
        block: suspend ClientSSESession.() -> Unit
    ): Result<Unit> {
        return tryResult(
            onError = { e -> e.toDivaNetworkException(url = Option.of(path)) }
        ) {
            client.sse(
                request = {
                    parseAndSetUrl(parseQueryParams(path, queryParams))
                    headers.forEach { (key, value) ->
                        this.header(key, value)
                    }
                }
            ) {
                block()
            }
        }
    }

    override suspend fun webSocket(
        path: String,
        queryParams: Map<String, String>,
        headers: Map<String, String>,
        block: suspend DefaultClientWebSocketSession.() -> Unit
    ): Result<Unit> {
        return tryResult(
            onError = { e -> e.toDivaNetworkException(url = Option.of(path)) }
        ) {
            client.webSocket(
                request = {
                    url(parseQueryParams(path, queryParams))
                    headers.forEach { (key, value) -> header(key, value) }
                }
            ) {
                block()
            }
        }
    }

    override suspend fun get(
        path: String,
        queryParams: Map<String, String>,
        headers: Map<String, String>,
        contentType: ContentType
    ): Result<HttpResponse> {
        return call(
            method = HttpMethod.Get,
            path = parseQueryParams(path, queryParams),
            headers = headers,
            contentType = contentType,
        )
    }

    override suspend fun post(
        path: String,
        headers: Map<String, String>,
        contentType: ContentType
    ): Result<HttpResponse> {
        return call(
            method = HttpMethod.Post,
            path = path,
            headers = headers,
            contentType = contentType,
        )
    }

    override suspend fun <T> post(
        path: String,
        body: T,
        headers: Map<String, String>,
        contentType: ContentType,
        serializer: KSerializer<T>,
    ): Result<HttpResponse> {
        return call(
            method = HttpMethod.Post,
            path = path,
            body = body,
            headers = headers,
            contentType = contentType,
            serializer = serializer
        )
    }

    override suspend fun <T> put(
        path: String,
        body: T,
        headers: Map<String, String>,
        contentType: ContentType,
        serializer: KSerializer<T>,
    ): Result<HttpResponse> {
        return call(
            method = HttpMethod.Put,
            path = path,
            body = body,
            headers = headers,
            contentType = contentType,
            serializer = serializer
        )
    }

    override suspend fun <T> patch(
        path: String,
        body: T,
        headers: Map<String, String>,
        contentType: ContentType,
        serializer: KSerializer<T>,
    ): Result<HttpResponse> {
        return call(
            method = HttpMethod.Patch,
            path = path,
            body = body,
            headers = headers,
            contentType = contentType,
            serializer = serializer
        )
    }

    override suspend fun delete(
        path: String,
        headers: Map<String, String>,
        contentType: ContentType,
    ): Result<HttpResponse> {
        return call(
            method = HttpMethod.Delete,
            path = path,
            headers = headers,
            contentType = contentType,
        )
    }

    override suspend fun <T> delete(
        path: String,
        body: T,
        headers: Map<String, String>,
        contentType: ContentType,
        serializer: KSerializer<T>,
    ): Result<HttpResponse> {
        return call(
            method = HttpMethod.Delete,
            path = path,
            body = body,
            headers = headers,
            contentType = contentType,
            serializer = serializer
        )
    }

    override suspend fun call(
        method: HttpMethod,
        path: String,
        headers: Map<String, String>,
        contentType: ContentType,
    ): Result<HttpResponse> {
        return tryResult(
            onError = { e ->
                e.toDivaNetworkException(url = Option.of(path))
            },
        ) {
            val request = client.request {
                this.method = method
                parseAndSetUrl(path)
                contentType(contentType)
                headers.forEach { (key, value) ->
                    this.header(key, value)
                }
            }
            request
        }
    }

    override suspend fun <T> call(
        method: HttpMethod,
        path: String,
        body: T,
        headers: Map<String, String>,
        contentType: ContentType,
        serializer: KSerializer<T>,
    ): Result<HttpResponse> {
        return tryResult(
            onError = { e ->
                e.toDivaNetworkException(url = Option.of(path))
            },
        ) {
            val request = client.request {
                this.method = method
                parseAndSetUrl(path)
                contentType(contentType)
                headers.forEach { (key, value) ->
                    this.header(key, value)
                }
                setBody(Json.encodeToString(serializer, body))
            }
            request
        }
    }

    private fun parseQueryParams(path: String, params: Map<String, String>): String {
        val fullPath = if (params.isNotEmpty()) {
            val params = params.entries.joinToString("&") { "${it.key}=${it.value}" }
            "$path?$params"
        } else {
            path
        }
        return fullPath
    }

    private fun HttpRequestBuilder.parseAndSetUrl(path: String) {
        if (path.startsWith("/")) {
            url {
                path(path)
            }
        } else {
            url(path)
        }
    }
}

fun<T : HttpClientEngineConfig> HttpClientConfig<T>.defaultConfig() {
    install(Logging) {
        logger = DivaClient.DEFAULT_LOGGER
        level = DivaClient.DEFAULT_LOG_LEVEL
    }

    install(HttpTimeout) {
        requestTimeoutMillis = DivaClient.DEFAULT_REQUEST_TIMEOUT
        connectTimeoutMillis = DivaClient.DEFAULT_CONNECT_TIMEOUT
        socketTimeoutMillis = DivaClient.DEFAULT_SOCKET_TIMEOUT
    }

    install(ResponseObserver) {
        onResponse { response ->
            DivaClient.DEFAULT_LOGGER.log("HTTP response: $response")
            DivaClient.DEFAULT_LOGGER.log("HTTP body: ${response.body<Any>()}")
            DivaClient.DEFAULT_LOGGER.log("HTTP status: ${response.status.value}")
        }
    }

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                encodeDefaults = true
                explicitNulls = true
            },
        )
    }
}
