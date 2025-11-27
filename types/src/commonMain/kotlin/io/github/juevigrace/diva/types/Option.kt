package io.github.juevigrace.diva.types

sealed interface Option<out T> {
    data class Some<T>(val value: T) : Option<T>

    object None : Option<Nothing>

    companion object {
        fun <T> of(value: T?): Option<T> = if (value != null) Some(value) else None
    }
}

fun <T, R> Option<T>.map(transform: (T) -> R): Option<R> {
    return when (this) {
        is Option.Some -> Option.Some(transform(value))
        is Option.None -> Option.None
    }
}

fun <T, R> Option<T>.flatMap(transform: (T) -> Option<R>): Option<R> {
    return when (this) {
        is Option.Some -> transform(value)
        is Option.None -> Option.None
    }
}

fun <T> Option<T>.getOrElse(default: () -> T): T {
    return when (this) {
        is Option.Some -> value
        is Option.None -> default()
    }
}

fun <T> Option<T>.isPresent(): Boolean {
    return when (this) {
        is Option.Some -> true
        is Option.None -> false
    }
}

fun <T> Option<T>.isEmpty(): Boolean = !isPresent()

fun <T> Option<T>.onSome(action: (T) -> Unit): Option<T> {
    if (this is Option.Some) action(value)
    return this
}
fun <T> Option<T>.onNone(action: () -> Unit): Option<T> {
    if (this is Option.None) action()
    return this
}

fun <T, R> Option<T>.fold(onSome: (T) -> R, onNone: () -> R): R {
    return when (this) {
        is Option.Some -> onSome(value)
        is Option.None -> onNone()
    }
}

fun <T> T?.toOption(): Option<T> {
    return Option.of(this)
}
