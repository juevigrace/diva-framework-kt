package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.core.types.DivaError
import io.github.juevigrace.diva.core.types.DivaResult
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

interface DivaClient {
    suspend fun <T> call(
        method: HttpMethod,
        url: String,
        headers: Map<String, String>,
        contentType: ContentType,
        serializer: KSerializer<T>,
    ): DivaResult<T, DivaError>

    suspend fun <T, B> call(
        method: HttpMethod,
        url: String,
        body: B,
        headers: Map<String, String>,
        contentType: ContentType,
        serializer: KSerializer<T>,
        bodySerializer: KSerializer<B>,
    ): DivaResult<T, DivaError>
}

suspend inline fun<reified T> DivaClient.get(
    url: String,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<T, DivaError> {
    return call(
        method = HttpMethod.Get,
        url = url,
        headers = headers,
        contentType = contentType,
        serializer = serializer(),
    )
}

suspend inline fun<reified T, reified B> DivaClient.post(
    url: String,
    body: B,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<T, DivaError> {
    return call(
        method = HttpMethod.Post,
        url = url,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer(),
        bodySerializer = serializer()
    )
}

suspend inline fun<reified T, reified B> DivaClient.put(
    url: String,
    body: B,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<T, DivaError> {
    return call(
        method = HttpMethod.Put,
        url = url,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer(),
        bodySerializer = serializer()
    )
}

suspend inline fun<reified T, reified B> DivaClient.patch(
    url: String,
    body: B,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<T, DivaError> {
    return call(
        method = HttpMethod.Patch,
        url = url,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer(),
        bodySerializer = serializer()
    )
}

suspend inline fun<reified T> DivaClient.delete(
    url: String,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<T, DivaError> {
    return call(
        method = HttpMethod.Delete,
        url = url,
        headers = headers,
        contentType = contentType,
        serializer = serializer(),
    )
}

suspend inline fun<reified T, reified B> DivaClient.delete(
    url: String,
    body: B,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json,
): DivaResult<T, DivaError> {
    return call(
        method = HttpMethod.Delete,
        url = url,
        body = body,
        headers = headers,
        contentType = contentType,
        serializer = serializer(),
        bodySerializer = serializer()
    )
}
