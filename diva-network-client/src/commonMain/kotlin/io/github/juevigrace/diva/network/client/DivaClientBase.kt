package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.map
import io.github.juevigrace.diva.core.tryResult
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Url
import io.ktor.http.buildUrl
import io.ktor.http.contentType
import io.ktor.http.parseUrl
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

abstract class DivaClientBase<C : HttpClientEngineConfig>(
    protected val engineFactory: HttpClientEngineFactory<C>,
    protected val httpClientConfig: HttpClientConfig<C>.() -> Unit = { defaultConfig() }
) : DivaClient {
    private var client: HttpClient = HttpClient(engineFactory, httpClientConfig)

    override fun config(config: HttpClientConfig<*>.() -> Unit): DivaResult<Unit, DivaError> {
        return tryResult(
            onError = { e ->
                e.toDivaError()
            }
        ) {
            client = client.config(config)
            DivaResult.success(Unit)
        }
    }

    override suspend fun call(
        method: HttpMethod,
        path: String,
        headers: Map<String, String>,
        contentType: ContentType,
    ): DivaResult<HttpResponse, DivaError> {
        return tryResult(
            onError = { e ->
                e.toDivaError(
                    ErrorCause.Error.Ex(
                        ex = e,
                        details = Option.Some(
                            "call method ${method.toHttpRequestMethod().name}, path $path"
                        )
                    )
                )
            },
        ) {
            parseFromPath(path).map { url ->
                val request: HttpResponse = createRequest(
                    method = method,
                    url = url,
                    headers = headers,
                    contentType = contentType,
                )
                request
            }
        }
    }

    override suspend fun <T> call(
        method: HttpMethod,
        path: String,
        body: T,
        headers: Map<String, String>,
        contentType: ContentType,
        serializer: KSerializer<T>,
    ): DivaResult<HttpResponse, DivaError> {
        return tryResult(
            onError = { e ->
                e.toDivaError(
                    ErrorCause.Error.Ex(
                        ex = e,
                        details = Option.Some(
                            "call method ${method.toHttpRequestMethod().name}, path $path"
                        )
                    )
                )
            },
        ) {
            parseFromPath(path).map { url ->
                val request: HttpResponse = createRequest(
                    method = method,
                    url = url,
                    headers = headers,
                    contentType = contentType,
                    bodyLambda = {
                        setBody(Json.encodeToString(serializer, body))
                    }
                )
                request
            }
        }
    }

    protected fun parseFromPath(path: String): DivaResult<Url, DivaError> {
        return tryResult(onError = { e -> e.toDivaError() }) {
            val url: Url = if (path.startsWith("/")) {
                buildUrl {
                    path(path)
                }
            } else {
                parseUrl(path)
                    ?: return DivaResult.failure(
                        DivaError(ErrorCause.Validation.Parse(field = "path"))
                    )
            }
            DivaResult.success(url)
        }
    }

    private suspend fun createRequest(
        method: HttpMethod,
        url: Url,
        headers: Map<String, String>,
        contentType: ContentType,
        bodyLambda: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        return client.request {
            this.method = method
            this.url(url)
            contentType(contentType)
            headers.forEach { (key, value) ->
                this.header(key, value)
            }
            bodyLambda()
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
