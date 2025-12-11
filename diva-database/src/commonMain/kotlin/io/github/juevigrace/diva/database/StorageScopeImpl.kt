package io.github.juevigrace.diva.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

internal class StorageScopeImpl<S : TransacterBase>(
    override val db: S
) : StorageScope<S> {
    override suspend fun <T : Any> getOne(query: Query<T>): DivaResult<T, DivaError> {
        return try {
            val result: T = query.executeAsOneOrNull()
                ?: return DivaResult.Companion.failure(DivaError.Companion.database("GET_ONE", null, "No result found"))
            DivaResult.Companion.success(result)
        } catch (e: Exception) {
            DivaResult.Companion.failure(DivaError.Companion.database("GET_ONE", null, e.message, e))
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
                        DivaResult.Companion.failure(
                            DivaError.Companion.database(
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
                            DivaResult.Companion.failure(
                                DivaError.Companion.database(
                                    operation = "GET_LIST_AS_FLOW",
                                    table = null,
                                    details = "No result found"
                                )
                            )
                        )
                    }
                    emit(DivaResult.Companion.success(entity))
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
            DivaResult.Companion.success(list)
        } catch (e: Exception) {
            DivaResult.Companion.failure(DivaError.Companion.database("GET_LIST", null, e.message, e))
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
                        DivaResult.Companion.failure(
                            DivaError.Companion.database(
                                operation = "GET_LIST_AS_FLOW",
                                table = null,
                                details = e.message,
                                cause = e
                            )
                        )
                    )
                }
                .collect { list ->
                    emit(DivaResult.Companion.success(list))
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
