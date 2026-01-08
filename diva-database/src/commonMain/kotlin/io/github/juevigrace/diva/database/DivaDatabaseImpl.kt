package io.github.juevigrace.diva.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.map
import io.github.juevigrace.diva.core.tryResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

internal class DivaDatabaseImpl<S : TransacterBase> (
    private val driver: SqlDriver,
    private val db: S,
) : DivaDatabase<S> {
    override suspend fun <T : Any> getOne(block: S.() -> Query<T>): DivaResult<Option<T>, DivaError> {
        return tryResult(
            onError = { e ->
                DivaError.exception(
                    e = e,
                    origin = "DivaDatabase.getOne"
                )
            }
        ) {
            val result: T? = block(db).executeAsOneOrNull()
            DivaResult.success(result)
        }.map { entity -> Option.of(entity) }
    }

    override fun <T : Any> getOneAsFlow(
        ctx: CoroutineContext,
        block: S.() -> Query<T>
    ): Flow<DivaResult<Option<T>, DivaError>> {
        return flow {
            block(db).asFlow()
                .mapToOneOrNull(ctx)
                .catch { e ->
                    emit(
                        DivaResult.failure(
                            DivaError.exception(
                                e = Exception(e.message, e),
                                origin = "DivaDatabase.getOneAsFlow"
                            )
                        )
                    )
                }
                .collect { entity ->
                    emit(DivaResult.success(entity))
                }
        }.flowOn(ctx).map { result -> result.map { entity -> Option.of(entity) } }
    }

    override suspend fun <T : Any> getList(block: S.() -> Query<T>): DivaResult<List<T>, DivaError> {
        return tryResult(
            onError = { e ->
                DivaError.exception(
                    e = e,
                    origin = "DivaDatabase.getList"
                )
            }
        ) {
            val list: List<T> = block(db).executeAsList()
            DivaResult.success(list)
        }
    }

    override fun <T : Any> getListAsFlow(
        ctx: CoroutineContext,
        block: S.() -> Query<T>
    ): Flow<DivaResult<List<T>, DivaError>> {
        return flow {
            block(db).asFlow()
                .mapToList(ctx)
                .catch { e ->
                    emit(
                        DivaResult.failure(
                            DivaError.exception(
                                e = Exception(e.message, e),
                                origin = "DivaDatabase.getListAsFlow"
                            )
                        )
                    )
                }
                .collect { list ->
                    emit(DivaResult.success(list))
                }
        }.flowOn(ctx)
    }

    override suspend fun <T : Any> use(block: suspend S.() -> DivaResult<T, DivaError>): DivaResult<T, DivaError> {
        return tryResult(
            onError = { e ->
                DivaError.exception(
                    e = e,
                    origin = "DivaDatabase.use"
                )
            }
        ) {
            block(db)
        }
    }

    override suspend fun checkHealth(): DivaResult<Boolean, DivaError> {
        return tryResult(
            onError = { e ->
                DivaError.exception(
                    e = e,
                    origin = "Storage.checkHealth"
                )
            }
        ) {
            driver.execute(null, "SELECT 1", 0).value
            DivaResult.success(true)
        }
    }

    override suspend fun close(): DivaResult<Unit, DivaError> {
        return tryResult(
            onError = { e ->
                DivaError.exception(
                    e = e,
                    origin = "Storage.close"
                )
            }
        ) {
            driver.close()
            DivaResult.success(Unit)
        }
    }
}
