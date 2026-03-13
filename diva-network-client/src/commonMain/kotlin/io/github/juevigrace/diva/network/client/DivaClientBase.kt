package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import io.github.juevigrace.diva.core.errors.toDivaError
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
            val request = client.request {
                this.method = method

                if (path.startsWith("/")) {
                    url {
                        path(path)
                    }
                } else {
                    url(path)
                }

                contentType(contentType)
                headers.forEach { (key, value) ->
                    this.header(key, value)
                }
            }
            DivaResult.success(request)
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
            val request = client.request {
                this.method = method

                if (path.startsWith("/")) {
                    url {
                        path(path)
                    }
                } else {
                    url(path)
                }

                contentType(contentType)
                headers.forEach { (key, value) ->
                    this.header(key, value)
                }
                setBody(Json.encodeToString(serializer, body))
            }
            DivaResult.success(request)
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
