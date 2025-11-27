package io.github.juevigrace.diva.database

import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface Storage<S> {
    suspend fun <T> withDatabase(
        ctx: CoroutineContext = EmptyCoroutineContext,
        block: Storage<S>.(S) -> T,
    ): T

    suspend fun <T> withDatabase(block: Storage<S>.(S) -> T): T
}

class StorageImpl<S>(private val db: S) : Storage<S> {
    override suspend fun <T> withDatabase(
        ctx: CoroutineContext,
        block: Storage<S>.(S) -> T
    ): T {
        return withContext(ctx) {
            block(db)
        }
    }

    override suspend fun <T> withDatabase(block: Storage<S>.(S) -> T): T {
        return withContext(EmptyCoroutineContext) {
            block(db)
        }
    }
}
