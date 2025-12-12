package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.tryResult
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlin.collections.component1
import kotlin.collections.component2

abstract class NetworkClientBase<C : HttpClientEngineConfig>(
    protected open val engineFactory: HttpClientEngineFactory<C>,
    protected open val conf: HttpClientConfig<C>.() -> Unit
) : NetworkClient {
    protected val client: HttpClient = HttpClient(engineFactory, conf)

    override suspend fun call(
        method: HttpMethod,
        url: String,
        headers: Map<String, String>,
        contentType: ContentType,
    ): DivaResult<HttpResponse, DivaError> {
        return tryResult(
            onError = { e ->
                DivaError.exception(
                    e = e,
                    origin = "NetworkClient.call"
                )
            },
        ) {
            val request: HttpResponse = createRequest(
                method = method,
                url = url,
                headers = headers,
                contentType = contentType,
            )
            DivaResult.success(request)
        }
    }

    override suspend fun <T> call(
        method: HttpMethod,
        url: String,
        body: T,
        headers: Map<String, String>,
        contentType: ContentType,
        serializer: KSerializer<T>,
    ): DivaResult<HttpResponse, DivaError> {
        return tryResult(
            onError = { e ->
                DivaError.exception(
                    e = e,
                    origin = "NetworkClient.call"
                )
            },
        ) {
            val request: HttpResponse = createRequest(
                method = method,
                url = url,
                headers = headers,
                contentType = contentType,
                bodyLambda = {
                    setBody(Json.encodeToString(serializer, body))
                }
            )
            DivaResult.success(request)
        }
    }

    private suspend fun createRequest(
        method: HttpMethod,
        url: String,
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
