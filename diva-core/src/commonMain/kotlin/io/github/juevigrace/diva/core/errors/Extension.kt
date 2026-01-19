package io.github.juevigrace.diva.core.errors

import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.network.HttpRequestMethod
import io.github.juevigrace.diva.core.network.HttpStatusCodes

fun Exception.toDivaError(origin: String? = null): DivaError {
    return if (this is DivaErrorException) {
        this.divaError
    } else {
        DivaError.exception(
            e = this,
            origin = origin,
        )
    }
}

// TODO: these casts are incomplete

fun DivaError.asNetworkError(
    method: HttpRequestMethod,
    url: String,
    statusCode: HttpStatusCodes = HttpStatusCodes.InternalServerError
): DivaError.NetworkError {
    return this as? DivaError.NetworkError ?: DivaError.NetworkError(
        method = method,
        url = url,
        statusCode = statusCode,
        details = message,
        cause = cause,
    )
}

fun DivaError.asDatabaseError(
    operation: DatabaseAction,
    table: String? = null,
): DivaError.DatabaseError {
    return this as? DivaError.DatabaseError ?: DivaError.DatabaseError(
        operation = operation,
        table = table,
        details = message,
        cause = cause,
    )
}

fun DivaError.asValidationError(
    field: String,
    constraint: String,
): DivaError.ValidationError {
    return this as? DivaError.ValidationError ?: DivaError.ValidationError(
        field = field,
        constraint = constraint,
        details = message,
    )
}

fun DivaError.asConfigurationError(
    key: String,
): DivaError.ConfigurationError {
    return this as? DivaError.ConfigurationError ?: DivaError.ConfigurationError(
        key = key,
        details = message,
    )
}

fun DivaError.asExceptionError(
    origin: String,
): DivaError.ExceptionError {
    return this as? DivaError.ExceptionError ?: DivaError.ExceptionError(
        message = message,
        cause = cause,
        origin = origin,
    )
}
