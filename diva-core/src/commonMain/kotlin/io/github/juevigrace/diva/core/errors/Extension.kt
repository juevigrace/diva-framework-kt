package io.github.juevigrace.diva.core.errors

import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.network.HttpRequestMethod
import io.github.juevigrace.diva.core.network.HttpStatusCodes

fun Exception.toDivaError(): DivaError {
    return DivaError.Error(cause = ErrorCause.Ex(this))
}

fun DivaError.toDatabaseError(
    action: DatabaseAction,
    table: Option<String> = Option.None
): DivaError.DatabaseError {
    return this as? DivaError.DatabaseError
        ?: DivaError.DatabaseError(
            action = action,
            table = table,
            cause = cause
        )
}

fun DivaError.toNetworkError(
    method: HttpRequestMethod,
    url: String,
    status: HttpStatusCodes = HttpStatusCodes.InternalServerError
): DivaError.NetworkError {
    return this as? DivaError.NetworkError
        ?: DivaError.NetworkError(
            method = method,
            url = url,
            status = status,
            cause = cause
        )
}
