package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

interface DivaClient {
    suspend fun call(
        method: HttpMethod,
        url: String,
        headers: Map<String, String>,
        contentType: ContentType,
    ): DivaResult<HttpResponse, DivaError>

    suspend fun <T> call(
        method: HttpMethod,
        url: String,
        body: T,
        headers: Map<String, String>,
        contentType: ContentType,
        serializer: KSerializer<T>,
    ): DivaResult<HttpResponse, DivaError>
}

suspend inline fun DivaClient.get(
    url: String,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<HttpResponse, DivaError> {
    return call(
        method = HttpMethod.Get,
        url = url,
        headers = headers,
        contentType = contentType,
    )
}

suspend inline fun<reified T> DivaClient.post(
    url: String,
    body: T,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<HttpResponse, DivaError> {
    return call(
        method = HttpMethod.Post,
        url = url,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer(),
    )
}

suspend inline fun<reified T> DivaClient.post(
    url: String,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<HttpResponse, DivaError> {
    return call(
        method = HttpMethod.Post,
        url = url,
        headers = headers,
        contentType = contentType,
    )
}

suspend inline fun<reified T> DivaClient.put(
    url: String,
    body: T,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<HttpResponse, DivaError> {
    return call(
        method = HttpMethod.Put,
        url = url,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer(),
    )
}

suspend inline fun<reified T> DivaClient.patch(
    url: String,
    body: T,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<HttpResponse, DivaError> {
    return call(
        method = HttpMethod.Patch,
        url = url,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer(),
    )
}

suspend inline fun DivaClient.delete(
    url: String,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<HttpResponse, DivaError> {
    return call(
        method = HttpMethod.Delete,
        url = url,
        headers = headers,
        contentType = contentType,
    )
}

suspend inline fun<reified T> DivaClient.delete(
    url: String,
    body: T,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<HttpResponse, DivaError> {
    return call(
        method = HttpMethod.Delete,
        url = url,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer(),
    )
}
