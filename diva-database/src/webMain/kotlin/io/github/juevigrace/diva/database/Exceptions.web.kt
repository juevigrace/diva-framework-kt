package io.github.juevigrace.diva.database

import io.github.juevigrace.diva.core.errors.DivaDatabaseException
import io.github.juevigrace.diva.core.util.logError

actual fun Throwable.toDivaDatabaseException(): DivaDatabaseException {
    val result = when (this) {
        is DivaDatabaseException -> this
        else -> DivaDatabaseException(message = message ?: "Unknown error", cause = this)
    }
    logError(result::class.simpleName ?: "DivaDatabaseException", result.message ?: result.toString())
    return result
}
