package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

interface DivaClient {
    suspend fun call(
        method: HttpMethod,
        path: String,
        headers: Map<String, String>,
        contentType: ContentType,
    ): DivaResult<HttpResponse, DivaError.NetworkError>

    suspend fun <T> call(
        method: HttpMethod,
        path: String,
        body: T,
        headers: Map<String, String>,
        contentType: ContentType,
        serializer: KSerializer<T>,
    ): DivaResult<HttpResponse, DivaError.NetworkError>
}

suspend inline fun DivaClient.get(
    path: String,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<HttpResponse, DivaError.NetworkError> {
    return call(
        method = HttpMethod.Get,
        path = path,
        headers = headers,
        contentType = contentType,
    )
}

suspend inline fun<reified T> DivaClient.post(
    path: String,
    body: T,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<HttpResponse, DivaError.NetworkError> {
    return call(
        method = HttpMethod.Post,
        path = path,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer(),
    )
}

suspend inline fun DivaClient.post(
    path: String,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<HttpResponse, DivaError.NetworkError> {
    return call(
        method = HttpMethod.Post,
        path = path,
        headers = headers,
        contentType = contentType,
    )
}

suspend inline fun<reified T> DivaClient.put(
    path: String,
    body: T,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<HttpResponse, DivaError.NetworkError> {
    return call(
        method = HttpMethod.Put,
        path = path,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer(),
    )
}

suspend inline fun<reified T> DivaClient.patch(
    path: String,
    body: T,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<HttpResponse, DivaError.NetworkError> {
    return call(
        method = HttpMethod.Patch,
        path = path,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer(),
    )
}

suspend inline fun DivaClient.delete(
    path: String,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<HttpResponse, DivaError.NetworkError> {
    return call(
        method = HttpMethod.Delete,
        path = path,
        headers = headers,
        contentType = contentType,
    )
}

suspend inline fun<reified T> DivaClient.delete(
    path: String,
    body: T,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<HttpResponse, DivaError.NetworkError> {
    return call(
        method = HttpMethod.Delete,
        path = path,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer(),
    )
}
