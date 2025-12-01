package io.github.juevigrace.diva.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.SuspendingTransacter
import app.cash.sqldelight.Transacter
import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import io.github.juevigrace.diva.types.DivaError
import io.github.juevigrace.diva.types.DivaResult
import io.github.juevigrace.diva.types.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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

internal class StorageScopeImpl<S : TransacterBase>(
    override val db: S
) : StorageScope<S> {
    override suspend fun <T : Any> getOne(query: Query<T>): DivaResult<T, DivaError> {
        return try {
            val result: T = query.executeAsOneOrNull()
                ?: return DivaResult.failure(DivaError.database("GET_ONE", null, "No result found"))
            DivaResult.success(result)
        } catch (e: Exception) {
            DivaResult.failure(DivaError.database("GET_ONE", null, e.message, e))
        }
    }

    override suspend fun <T : Any, R : Any> getOne(
        query: Query<T>,
        mapper: (T) -> R
    ): DivaResult<R, DivaError> {
        return getOne(query).map { entity -> mapper(entity) }
    }

    override fun <T : Any> getOneAsFlow(
        query: Query<T>,
        ctx: CoroutineContext
    ): Flow<DivaResult<T, DivaError>> {
        return flow {
            query.asFlow()
                .mapToOneOrNull(ctx)
                .catch { e ->
                    emit(
                        DivaResult.failure(
                            DivaError.database(
                                operation = "GET_LIST_AS_FLOW",
                                table = null,
                                details = e.message,
                                cause = e
                            )
                        )
                    )
                }
                .collect { entity ->
                    if (entity == null) {
                        return@collect emit(
                            DivaResult.failure(
                                DivaError.database(
                                    operation = "GET_LIST_AS_FLOW",
                                    table = null,
                                    details = "No result found"
                                )
                            )
                        )
                    }
                    emit(DivaResult.success(entity))
                }
        }.flowOn(ctx)
    }

    override fun <T : Any, R : Any> getOneAsFlow(
        query: Query<T>,
        mapper: (T) -> R,
        ctx: CoroutineContext
    ): Flow<DivaResult<R, DivaError>> {
        return getOneAsFlow(query, ctx).map { result -> result.map { entity -> mapper(entity) } }
    }

    override suspend fun <T : Any> getList(query: Query<T>): DivaResult<List<T>, DivaError> {
        return try {
            val list: List<T> = query.executeAsList()
            DivaResult.success(list)
        } catch (e: Exception) {
            DivaResult.failure(DivaError.database("GET_LIST", null, e.message, e))
        }
    }

    override suspend fun <T : Any, R : Any> getList(
        query: Query<T>,
        mapper: (T) -> R
    ): DivaResult<List<R>, DivaError> {
        return getList(query).map { list -> list.map(mapper) }
    }

    override fun <T : Any> getListAsFlow(
        query: Query<T>,
        ctx: CoroutineContext
    ): Flow<DivaResult<List<T>, DivaError>> {
        return flow {
            query.asFlow()
                .mapToList(ctx)
                .catch { e ->
                    emit(
                        DivaResult.failure(
                            DivaError.database(
                                operation = "GET_LIST_AS_FLOW",
                                table = null,
                                details = e.message,
                                cause = e
                            )
                        )
                    )
                }
                .collect { list ->
                    emit(DivaResult.success(list))
                }
        }.flowOn(ctx)
    }

    override fun <T : Any, R : Any> getListAsFlow(
        query: Query<T>,
        mapper: (T) -> R,
        ctx: CoroutineContext
    ): Flow<DivaResult<List<R>, DivaError>> {
        return getListAsFlow(query, ctx).map { result -> result.map { list -> list.map(mapper) } }
    }
}
