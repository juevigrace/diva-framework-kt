package io.github.juevigrace.diva.core

inline fun<T> tryResult(onError: (Exception) -> Exception, block: () -> Result<T>): Result<T> {
    return try {
        block()
    } catch (e: Exception) {
        Result.failure(onError(e))
    }
}
