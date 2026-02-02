package io.github.juevigrace.diva.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
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
    override suspend fun <T : Any> getOne(
        onError: (Exception) -> DivaError.DatabaseError,
        block: S.() -> Query<T>
    ): DivaResult<Option<T>, DivaError.DatabaseError> {
        return tryResult(
            onError = onError
        ) {
            val result: T? = block(db).executeAsOneOrNull()
            DivaResult.success(Option.of(result))
        }
    }

    override fun <T : Any> getOneAsFlow(
        ctx: CoroutineContext,
        onError: (Exception) -> DivaError.DatabaseError,
        block: S.() -> Query<T>
    ): Flow<DivaResult<Option<T>, DivaError.DatabaseError>> {
        return flow {
            block(db).asFlow().mapToOneOrNull(ctx).catch { e ->
                emit(DivaResult.failure(onError(Exception(e))))
            }.collect { entity ->
                emit(DivaResult.success(Option.of(entity)))
            }
        }.flowOn(ctx)
    }

    override suspend fun <T : Any> getList(
        onError: (Exception) -> DivaError.DatabaseError,
        block: S.() -> Query<T>
    ): DivaResult<List<T>, DivaError.DatabaseError> {
        return tryResult(
            onError = onError
        ) {
            val list: List<T> = block(db).executeAsList()
            DivaResult.success(list)
        }
    }

    override fun <T : Any> getListAsFlow(
        ctx: CoroutineContext,
        onError: (Exception) -> DivaError.DatabaseError,
        block: S.() -> Query<T>
    ): Flow<DivaResult<List<T>, DivaError.DatabaseError>> {
        return flow {
            block(db).asFlow().mapToList(ctx).catch { e ->
                emit(DivaResult.failure(onError(Exception(e))))
            }.collect { list ->
                emit(DivaResult.success(list))
            }
        }.flowOn(ctx)
    }

    override suspend fun <T : Any> use(
        onError: (Exception) -> DivaError.DatabaseError,
        block: suspend S.() -> DivaResult<T, DivaError.DatabaseError>
    ): DivaResult<T, DivaError.DatabaseError> {
        return tryResult(
            onError = onError
        ) {
            block(db)
        }
    }

    override suspend fun <T : Any> withDriver(
        onError: (Exception) -> DivaError.DatabaseError,
        block: suspend SqlDriver.() -> DivaResult<T, DivaError.DatabaseError>
    ): DivaResult<T, DivaError.DatabaseError> {
        return tryResult(
            onError = onError
        ) {
            block(driver)
        }
    }

    override suspend fun checkHealth(
        onError: (Exception) -> DivaError.DatabaseError
    ): DivaResult<Boolean, DivaError.DatabaseError> {
        return tryResult(
            onError = onError
        ) {
            driver.execute(null, "SELECT 1", 0).value
            DivaResult.success(true)
        }
    }

    override suspend fun close(
        onError: (Exception) -> DivaError.DatabaseError
    ): DivaResult<Unit, DivaError.DatabaseError> {
        return tryResult(
            onError = onError
        ) {
            driver.close()
            DivaResult.success(Unit)
        }
    }
}
