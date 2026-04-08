package io.github.juevigrace.diva.core

inline fun<T> tryResult(onError: (Exception) -> Exception = { e -> e }, block: () -> T): Result<T> {
    return try {
        val value: T = block()
        Result.success(value)
    } catch (e: Exception) {
        Result.failure(onError(e))
    }
}

inline fun<T> tryResult(block: () -> T): Result<T> {
    return try {
        val value: T = block()
        Result.success(value)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
