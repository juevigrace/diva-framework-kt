package io.github.juevigrace.diva.types

sealed interface Option<out T> {
    data class Some<T>(val value: T) : Option<T>

    object None : Option<Nothing>

    companion object {
        fun <T> of(value: T?): Option<T> = if (value != null) Some(value) else None
    }
}

fun <T> Option<T>.getOrElse(default: () -> T): T =
    when (this) {
        is Option.Some -> value
        is Option.None -> default()
    }

fun <T, R> Option<T>.map(transform: (T) -> R): Option<R> =
    when (this) {
        is Option.Some -> Option.Some(transform(value))
        is Option.None -> Option.None
    }

fun <T, R> Option<T>.flatMap(transform: (T) -> Option<R>): Option<R> =
    when (this) {
        is Option.Some -> transform(value)
        is Option.None -> Option.None
    }

fun <T> Option<T>.isPresent(): Boolean =
    when (this) {
        is Option.Some -> true
        is Option.None -> false
    }

fun <T> Option<T>.isEmpty(): Boolean = !isPresent()
