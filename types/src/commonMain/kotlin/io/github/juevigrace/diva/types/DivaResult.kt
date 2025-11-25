package io.github.juevigrace.diva.types

sealed interface DivaResult<out T, out E> {
    data class Success<T>(val value: T) : DivaResult<T, Nothing>

    data class Failure<E>(val error: E) : DivaResult<Nothing, E>

    companion object {
        fun <T> success(value: T): DivaResult<T, Nothing> = Success(value)

        fun <E> failure(error: E): DivaResult<Nothing, E> = Failure(error)
    }
}

fun <T, E, R> DivaResult<T, E>.map(transform: (T) -> R): DivaResult<R, E> =
    when (this) {
        is DivaResult.Success -> DivaResult.Success(transform(value))
        is DivaResult.Failure -> this
    }

fun <T, E, R> DivaResult<T, E>.flatMap(transform: (T) -> DivaResult<R, E>): DivaResult<R, E> =
    when (this) {
        is DivaResult.Success -> transform(value)
        is DivaResult.Failure -> this
    }

fun <T, E> DivaResult<T, E>.getOrElse(default: () -> T): T = if (this is DivaResult.Success) value else default()

fun <T, E> DivaResult<T, E>.mapError(transform: (E) -> E): DivaResult<T, E> =
    if (this is DivaResult.Failure) DivaResult.Failure(transform(error)) else this

fun <T, E> DivaResult<T, E>.isSuccess(): Boolean = this is DivaResult.Success

fun <T, E> DivaResult<T, E>.isFailure(): Boolean = !isSuccess()

fun <T, E> DivaResult<T, E>.getOrNull(): T? = if (this is DivaResult.Success) value else null

fun <T, E> DivaResult<T, E>.getErrorOrNull(): E? = if (this is DivaResult.Failure) error else null
