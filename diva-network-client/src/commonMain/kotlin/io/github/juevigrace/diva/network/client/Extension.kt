package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.DivaErrorException
import io.github.juevigrace.diva.core.network.HttpRequestMethod
import io.github.juevigrace.diva.core.network.HttpStatusCodes
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode

fun HttpMethod.toHttpRequestMethod(): HttpRequestMethod {
    return when (value) {
        HttpMethod.Get.value -> HttpRequestMethod.GET
        HttpMethod.Post.value -> HttpRequestMethod.POST
        HttpMethod.Put.value -> HttpRequestMethod.PUT
        HttpMethod.Delete.value -> HttpRequestMethod.DELETE
        HttpMethod.Patch.value -> HttpRequestMethod.PATCH
        HttpMethod.Head.value -> HttpRequestMethod.HEAD
        HttpMethod.Options.value -> HttpRequestMethod.OPTIONS
        else -> throw DivaErrorException(DivaError.exception(Exception("Unsupported HTTP method: $this")))
    }
}

fun HttpStatusCode.toHttpStatusCodes(): HttpStatusCodes {
    return HttpStatusCodes.fromInt(this.value)
}
