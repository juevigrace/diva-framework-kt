package io.github.juevigrace.diva.core.types

sealed interface DivaResult<out T, out E> {
    data class Success<T>(val value: T) : DivaResult<T, Nothing>

    data class Failure<E>(val err: E) : DivaResult<Nothing, E>

    companion object {
        fun <T> success(value: T): DivaResult<T, Nothing> {
            return Success(value)
        }

        fun <E> failure(error: E): DivaResult<Nothing, E> {
            return Failure(error)
        }
    }
}

fun <T, E, R> DivaResult<T, E>.map(transform: (T) -> R): DivaResult<R, E> {
    return when (this) {
        is DivaResult.Success -> DivaResult.Success(transform(value))
        is DivaResult.Failure -> this
    }
}

fun <T, E, R> DivaResult<T, E>.flatMap(transform: (T) -> DivaResult<R, E>): DivaResult<R, E> {
    return when (this) {
        is DivaResult.Success -> transform(value)
        is DivaResult.Failure -> this
    }
}

fun <T, E> DivaResult<T, E>.mapError(transform: (E) -> E): DivaResult<T, E> {
    return when (this) {
        is DivaResult.Success -> this
        is DivaResult.Failure -> DivaResult.Failure(transform(err))
    }
}

fun <T, E, R> DivaResult<T, E>.flatMapError(transform: (E) -> DivaResult<T, R>): DivaResult<T, R> {
    return when (this) {
        is DivaResult.Success -> this
        is DivaResult.Failure -> transform(err)
    }
}

fun <T, E> DivaResult<T, E>.isSuccess(): Boolean {
    return this is DivaResult.Success
}

fun <T, E> DivaResult<T, E>.isFailure(): Boolean {
    return !isSuccess()
}

fun <T, E> DivaResult<T, E>.getOrNull(): T? {
    return when (this) {
        is DivaResult.Success -> value
        is DivaResult.Failure -> null
    }
}

fun <T, E> DivaResult<T, E>.getErrorOrNull(): E? {
    return when (this) {
        is DivaResult.Success -> null
        is DivaResult.Failure -> err
    }
}

fun <T, E> DivaResult<T, E>.getOrElse(default: () -> T): T {
    return when (this) {
        is DivaResult.Success -> value
        is DivaResult.Failure -> default()
    }
}

fun <T, E> DivaResult<T, E>.getOrThrow(): T {
    return when (this) {
        is DivaResult.Success -> value
        is DivaResult.Failure -> error(err.toString())
    }
}

fun <T, E, R> DivaResult<T, E>.fold(onSuccess: (T) -> R, onFailure: (E) -> R): R {
    return when (this) {
        is DivaResult.Success -> onSuccess(value)
        is DivaResult.Failure -> onFailure(err)
    }
}

fun <T, E> DivaResult<T, E>.onSuccess(action: (T) -> Unit): DivaResult<T, E> {
    return when (this) {
        is DivaResult.Success -> {
            action(value)
            this
        }

        is DivaResult.Failure -> this
    }
}

fun <T, E> DivaResult<T, E>.onFailure(action: (E) -> Unit): DivaResult<T, E> {
    return when (this) {
        is DivaResult.Success -> this
        is DivaResult.Failure -> {
            action(err)
            this
        }
    }
}
