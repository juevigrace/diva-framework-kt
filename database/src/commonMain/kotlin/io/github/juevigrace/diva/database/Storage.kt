package io.github.juevigrace.diva.database

interface Storage {
    fun<T> createStorage(): T
}
