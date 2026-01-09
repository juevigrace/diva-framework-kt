package io.github.juevigrace.diva.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.asDatabaseError
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.tryResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

internal class DivaDatabaseImpl<S : TransacterBase>(
    private val driver: SqlDriver,
    private val db: S,
) : DivaDatabase<S> {
    override suspend fun <T : Any> getOne(block: S.() -> Query<T>): DivaResult<Option<T>, DivaError.DatabaseError> {
        return tryResult(
            onError = { e ->
                e.toDivaError("DivaDatabase.getOne").asDatabaseError(DatabaseAction.SELECT)
            }
        ) {
            val result: T? = block(db).executeAsOneOrNull()
            DivaResult.success(Option.of(result))
        }
    }

    override fun <T : Any> getOneAsFlow(
        ctx: CoroutineContext,
        block: S.() -> Query<T>
    ): Flow<DivaResult<Option<T>, DivaError.DatabaseError>> {
        return flow {
            block(db).asFlow().mapToOneOrNull(ctx).catch { e ->
                emit(
                    DivaResult.failure(
                        Exception(e).toDivaError("DivaDatabase.getOneAsFlow")
                            .asDatabaseError(DatabaseAction.SELECT)
                    )
                )
            }.collect { entity ->
                emit(DivaResult.success(Option.of(entity)))
            }
        }.flowOn(ctx)
    }

    override suspend fun <T : Any> getList(block: S.() -> Query<T>): DivaResult<List<T>, DivaError.DatabaseError> {
        return tryResult(
            onError = { e ->
                e.toDivaError("DivaDatabase.getOne").asDatabaseError(DatabaseAction.SELECT)
            }
        ) {
            val list: List<T> = block(db).executeAsList()
            DivaResult.success(list)
        }
    }

    override fun <T : Any> getListAsFlow(
        ctx: CoroutineContext,
        block: S.() -> Query<T>
    ): Flow<DivaResult<List<T>, DivaError.DatabaseError>> {
        return flow {
            block(db).asFlow().mapToList(ctx).catch { e ->
                emit(
                    DivaResult.failure(
                        Exception(e).toDivaError("DivaDatabase.getOneAsFlow")
                            .asDatabaseError(DatabaseAction.SELECT)
                    )

                )
            }.collect { list ->
                emit(DivaResult.success(list))
            }
        }.flowOn(ctx)
    }

    override suspend fun <T : Any> use(
        block: suspend S.() -> DivaResult<T, DivaError.DatabaseError>
    ): DivaResult<T, DivaError.DatabaseError> {
        return tryResult(
            onError = { e ->
                e.toDivaError("DivaDatabase.use").asDatabaseError(DatabaseAction.USE)
            }
        ) {
            block(db)
        }
    }

    override suspend fun checkHealth(): DivaResult<Boolean, DivaError.DatabaseError> {
        return tryResult(
            onError = { e ->
                e.toDivaError("DivaDatabase.checkHealth").asDatabaseError(DatabaseAction.SELECT)
            }
        ) {
            driver.execute(null, "SELECT 1", 0).value
            DivaResult.success(true)
        }
    }

    override suspend fun close(): DivaResult<Unit, DivaError.DatabaseError> {
        return tryResult(
            onError = { e ->
                e.toDivaError("DivaDatabase.close").asDatabaseError(DatabaseAction.CLOSE)
            }
        ) {
            driver.close()
            DivaResult.success(Unit)
        }
    }
}
