package io.github.juevigrace.diva.network.client

import io.github.juevigrace.diva.core.errors.ErrorCause
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
        else -> HttpRequestMethod.UNSPECIFIED
    }
}

fun HttpStatusCode.toHttpStatusCodes(): HttpStatusCodes {
    return HttpStatusCodes.fromInt(this.value)
}

fun ErrorCause.toHttpStatusCodes(): HttpStatusCodes {
    return when (this) {
        is ErrorCause.Database.Duplicated -> {
            HttpStatusCodes.Conflict
        }
        is ErrorCause.Error.Ex -> {
            HttpStatusCodes.InternalServerError
        }
        is ErrorCause.Error.NotImplemented -> {
            HttpStatusCodes.NotImplemented
        }
        is ErrorCause.Validation.MissingValue, is ErrorCause.Database.NoRowsAffected -> {
            HttpStatusCodes.NotFound
        }
        is ErrorCause.Validation.UnexpectedValue,
        is ErrorCause.Validation.Parse,
        is ErrorCause.Validation.Expired,
        is ErrorCause.Validation.Used -> {
            HttpStatusCodes.BadRequest
        }
        is ErrorCause.Network -> status
    }
}

fun HttpStatusCodes.toKtorStatusCodes(): HttpStatusCode {
    return HttpStatusCode.fromValue(code)
}
