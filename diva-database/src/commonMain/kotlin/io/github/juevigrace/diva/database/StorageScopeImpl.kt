package io.github.juevigrace.diva.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import io.github.juevigrace.diva.core.models.map
import io.github.juevigrace.diva.core.models.tryResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

internal class StorageScopeImpl<S : TransacterBase>(
    override val db: S
) : StorageScope<S> {
    private fun <T : Any> getOneInternal(query: Query<T>): DivaResult<T?, DivaError> {
        return tryResult(
            onError = { e ->
                DivaError.exception(
                    e = e,
                    origin = "StorageScope.getOne"
                )
            }
        ) {
            val result: T? = query.executeAsOneOrNull()
            DivaResult.success(result)
        }
    }

    override suspend fun <T : Any> getOne(query: Query<T>): DivaResult<Option<T>, DivaError> {
        return getOneInternal(query).map { entity -> Option.of(entity) }
    }

    private fun<T : Any> getOneAsFlowInternal(query: Query<T>, ctx: CoroutineContext): Flow<DivaResult<T?, DivaError>> {
        return flow {
            query.asFlow()
                .mapToOneOrNull(ctx)
                .catch { e ->
                    emit(
                        DivaResult.failure(
                            DivaError.exception(
                                e = Exception(e.message, e),
                                origin = "StorageScope.getOneAsFlow"
                            )
                        )
                    )
                }
                .collect { entity ->
                    emit(DivaResult.success(entity))
                }
        }.flowOn(ctx)
    }

    override fun <T : Any> getOneAsFlow(
        query: Query<T>,
        ctx: CoroutineContext
    ): Flow<DivaResult<Option<T>, DivaError>> {
        return getOneAsFlowInternal(query, ctx).map { result -> result.map { entity -> Option.of(entity) } }
    }

    override fun <T : Any, R : Any> getOneAsFlow(
        query: Query<T>,
        mapper: (T) -> R,
        ctx: CoroutineContext
    ): Flow<DivaResult<Option<R>, DivaError>> {
        return getOneAsFlowInternal(query, ctx).map { result ->
            result.map { entity ->
                if (entity == null) {
                    Option.None
                } else {
                    Option.of(mapper(entity))
                }
            }
        }
    }

    override suspend fun <T : Any> getList(query: Query<T>): DivaResult<List<T>, DivaError> {
        return tryResult(
            onError = { e ->
                DivaError.exception(
                    e = e,
                    origin = "StorageScope.getList"
                )
            }
        ) {
            val list: List<T> = query.executeAsList()
            DivaResult.success(list)
        }
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
                            DivaError.exception(
                                e = Exception(e.message, e),
                                origin = "StorageScope.getListAsFlow"
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
