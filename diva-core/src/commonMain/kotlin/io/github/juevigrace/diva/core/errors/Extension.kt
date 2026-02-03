package io.github.juevigrace.diva.core.errors

fun Exception.toDivaError(cause: ErrorCause = ErrorCause.Error.Ex(this)): DivaError {
    return DivaError(cause = cause)
}
