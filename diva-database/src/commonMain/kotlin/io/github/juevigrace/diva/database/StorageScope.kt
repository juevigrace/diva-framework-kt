package io.github.juevigrace.diva.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.SuspendingTransacter
import app.cash.sqldelight.Transacter
import app.cash.sqldelight.TransacterBase
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Scope interface containing all database operations.
 * These functions can only be used within a withDb block.
 */
interface StorageScope<S : TransacterBase> {
    val db: S

    suspend fun <T : Any> getOne(query: Query<T>): DivaResult<T, DivaError>

    suspend fun <T : Any, R : Any> getOne(
        query: Query<T>,
        mapper: (T) -> R,
    ): DivaResult<R, DivaError>

    fun <T : Any> getOneAsFlow(
        query: Query<T>,
        ctx: CoroutineContext = EmptyCoroutineContext,
    ): Flow<DivaResult<T, DivaError>>

    fun <T : Any, R : Any> getOneAsFlow(
        query: Query<T>,
        mapper: (T) -> R,
        ctx: CoroutineContext = EmptyCoroutineContext,
    ): Flow<DivaResult<R, DivaError>>

    suspend fun <T : Any> getList(query: Query<T>): DivaResult<List<T>, DivaError>

    suspend fun <T : Any, R : Any> getList(
        query: Query<T>,
        mapper: (T) -> R,
    ): DivaResult<List<R>, DivaError>

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

fun StorageScope<Transacter>.transaction(block: () -> Unit): DivaResult<Unit, DivaError> {
    return try {
        db.transaction { block() }
        DivaResult.success(Unit)
    } catch (e: Exception) {
        DivaResult.failure(DivaError.database("TRANSACTION", null, e.message, e))
    }
}

fun <T : Any> StorageScope<Transacter>.transactionWithResult(
    block: () -> T
): DivaResult<T, DivaError> {
    return try {
        val result: T = db.transactionWithResult { block() }
        DivaResult.success(result)
    } catch (e: Exception) {
        DivaResult.failure(DivaError.database("TRANSACTION_WITH_RESULT", null, e.message, e))
    }
}

suspend fun StorageScope<SuspendingTransacter>.transaction(block: suspend () -> Unit): DivaResult<Unit, DivaError> {
    return try {
        db.transaction { block() }
        DivaResult.success(Unit)
    } catch (e: Exception) {
        DivaResult.failure(DivaError.database("TRANSACTION", null, e.message, e))
    }
}

suspend fun <T : Any> StorageScope<SuspendingTransacter>.transactionWithResult(
    block: suspend () -> T
): DivaResult<T, DivaError> {
    return try {
        val result: T = db.transactionWithResult { block() }
        DivaResult.success(result)
    } catch (e: Exception) {
        DivaResult.failure(DivaError.database("TRANSACTION_WITH_RESULT", null, e.message, e))
    }
}
