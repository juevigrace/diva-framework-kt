package io.github.juevigrace.diva.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.SuspendingTransacter
import app.cash.sqldelight.Transacter
import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.TransactionWithReturn
import app.cash.sqldelight.TransactionWithoutReturn
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import io.github.juevigrace.diva.core.models.tryResult
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

    suspend fun <T : Any, R : Any> getOne(
        query: Query<T>,
        mapper: (T) -> R,
    ): DivaResult<Option<R>, DivaError>

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

inline fun StorageScope<Transacter>.transaction(
    crossinline block: TransactionWithoutReturn.() -> Unit
): DivaResult<Unit, DivaError> {
    return tryResult(
        onError = { e ->
            DivaError.exception(
                e = e,
                origin = "StorageScope.transaction"
            )
        }
    ) {
        val transactionResult: Unit = db.transaction { block() }
        DivaResult.success(transactionResult)
    }
}

inline fun <T : Any> StorageScope<Transacter>.transactionWithResult(
    crossinline block: TransactionWithReturn<T>.() -> T
): DivaResult<T, DivaError> {
    return tryResult(
        onError = { e ->
            DivaError.exception(
                e = e,
                origin = "StorageScope.transactionWithResult"
            )
        }
    ) {
        val result: T = db.transactionWithResult { block() }
        DivaResult.success(result)
    }
}

suspend inline fun StorageScope<SuspendingTransacter>.transaction(
    crossinline block: () -> Unit
): DivaResult<Unit, DivaError> {
    return tryResult(
        onError = { e ->
            DivaError.exception(
                e = e,
                origin = "StorageScope.transaction"
            )
        }
    ) {
        val transactionResult: Unit = db.transaction { block() }
        DivaResult.success(transactionResult)
    }
}

suspend inline fun <T : Any> StorageScope<SuspendingTransacter>.transactionWithResult(
    crossinline block: suspend () -> T
): DivaResult<T, DivaError> {
    return tryResult(
        onError = { e ->
            DivaError.exception(
                e = e,
                origin = "StorageScope.transactionWithResult"
            )
        }
    ) {
        val result: T = db.transactionWithResult { block() }
        DivaResult.success(result)
    }
}
