package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

// TODO: response and request body will only be json for now
abstract class NetworkClientBase<C : HttpClientEngineConfig>(
    protected open val engineFactory: HttpClientEngineFactory<C>,
    protected open val conf: HttpClientConfig<C>.() -> Unit
) : NetworkClient {
    protected val client: HttpClient = HttpClient(engineFactory, conf)

    override suspend fun <T> call(
        method: HttpMethod,
        url: String,
        headers: Map<String, String>,
        contentType: ContentType,
        serializer: KSerializer<T>
    ): DivaResult<T, DivaError> {
        return try {
            val request: HttpResponse = client.request {
                this.method = method
                this.url(url)
                contentType(contentType)
                headers.forEach { (key, value) ->
                    this.header(key, value)
                }
            }
            call(request, serializer)
        } catch (e: Exception) {
            DivaResult.failure(
                DivaError.network(
                    "CALL",
                    url = url,
                    statusCode = 500,
                    details = e.message,
                    cause = e,
                )
            )
        }
    }

    override suspend fun <T, B> call(
        method: HttpMethod,
        url: String,
        body: B,
        headers: Map<String, String>,
        contentType: ContentType,
        serializer: KSerializer<T>,
        bodySerializer: KSerializer<B>,
    ): DivaResult<T, DivaError> {
        return try {
            val request: HttpResponse = client.request {
                this.method = method
                this.url(url)
                contentType(contentType)
                headers.forEach { (key, value) ->
                    this.header(key, value)
                }
                setBody(Json.encodeToString(bodySerializer, body))
            }
            call(request, serializer)
        } catch (e: Exception) {
            DivaResult.failure(
                DivaError.network(
                    "CALL",
                    url = url,
                    statusCode = 500,
                    details = e.message,
                    cause = e,
                )
            )
        }
    }

    private suspend fun <T> call(
        response: HttpResponse,
        serializer: KSerializer<T>
    ): DivaResult<T, DivaError> {
        return try {
            val body: T = Json.decodeFromString(serializer, response.bodyAsText())
            DivaResult.success(body)
        } catch (e: Exception) {
            DivaResult.failure(
                DivaError.network(
                    "CALL",
                    url = response.request.url.toString(),
                    statusCode = response.status.value,
                    details = e.message,
                    cause = e,
                )
            )
        }
    }
}
