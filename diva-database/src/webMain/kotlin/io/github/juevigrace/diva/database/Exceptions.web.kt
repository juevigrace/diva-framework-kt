package io.github.juevigrace.diva.database

import io.github.juevigrace.diva.core.errors.DivaDatabaseException

actual fun Throwable.toDivaDatabaseException(): DivaDatabaseException {
    return when (this) {
        is DivaDatabaseException -> this
        else -> DivaDatabaseException(message = message ?: "Unknown error", cause = this)
    }
}
