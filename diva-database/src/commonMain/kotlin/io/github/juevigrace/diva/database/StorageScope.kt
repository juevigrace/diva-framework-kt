package io.github.juevigrace.diva.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterBase
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Scope interface containing all database operations.
 * These functions can only be used within a withDb block.
 */
interface StorageScope<S : TransacterBase> {
    val db: S

    suspend fun <T : Any> getOne(query: Query<T>): DivaResult<Option<T>, DivaError>

    fun <T : Any> getOneAsFlow(
        query: Query<T>,
        ctx: CoroutineContext = EmptyCoroutineContext,
    ): Flow<DivaResult<Option<T>, DivaError>>

    fun <T : Any, R : Any> getOneAsFlow(
        query: Query<T>,
        mapper: (T) -> R,
        ctx: CoroutineContext = EmptyCoroutineContext,
    ): Flow<DivaResult<Option<R>, DivaError>>

    suspend fun <T : Any> getList(query: Query<T>): DivaResult<List<T>, DivaError>

    fun <T : Any> getListAsFlow(
        query: Query<T>,
        ctx: CoroutineContext = EmptyCoroutineContext,
    ): Flow<DivaResult<List<T>, DivaError>>

    fun <T : Any, R : Any> getListAsFlow(
        query: Query<T>,
        mapper: (T) -> R,
        ctx: CoroutineContext = EmptyCoroutineContext,
    ): Flow<DivaResult<List<R>, DivaError>>

    companion object {
        operator fun <S : TransacterBase> invoke(db: S): StorageScope<S> = StorageScopeImpl(db)
    }
}
