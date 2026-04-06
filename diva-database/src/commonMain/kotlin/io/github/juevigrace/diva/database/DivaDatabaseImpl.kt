package io.github.juevigrace.diva.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.tryResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

internal class DivaDatabaseImpl<S : TransacterBase>(
    private val driver: SqlDriver,
    private val db: S,
) : DivaDatabase<S> {
    override suspend fun <T : Any> getOne(
        block: S.() -> Query<T>
    ): Result<Option<T>> {
        return tryResult(
            onError = { e ->
                e.toDivaDatabaseException()
            }
        ) {
            val result: T? = block(db).executeAsOneOrNull()
            Result.success(Option.of(result))
        }
    }

    override fun <T : Any> getOneAsFlow(
        ctx: CoroutineContext,
        block: S.() -> Query<T>
    ): Flow<Result<Option<T>>> {
        return block(db).asFlow()
            .mapToOneOrNull(ctx)
            .catch { e ->
                Result.failure<Option<T>>(e.toDivaDatabaseException())
            }
            .map { entity ->
                Result.success(Option.of(entity))
            }
    }

    override suspend fun <T : Any> getList(
        block: S.() -> Query<T>
    ): Result<List<T>> {
        return tryResult(
            onError = { e ->
                e.toDivaDatabaseException()
            }
        ) {
            val list: List<T> = block(db).executeAsList()
            Result.success(list)
        }
    }

    override fun <T : Any> getListAsFlow(
        ctx: CoroutineContext,
        block: S.() -> Query<T>
    ): Flow<Result<List<T>>> {
        return block(db).asFlow()
            .mapToList(ctx)
            .catch { e ->
                Result.failure<List<T>>(e.toDivaDatabaseException())
            }
            .map { list ->
                Result.success(list)
            }
    }

    override suspend fun <T : Any> use(
        block: suspend S.() -> Result<T>
    ): Result<T> {
        return tryResult(
            onError = { e ->
                e.toDivaDatabaseException()
            }
        ) {
            block(db)
        }
    }

    override suspend fun <T : Any> withDriver(
        block: suspend SqlDriver.() -> Result<T>
    ): Result<T> {
        return tryResult(
            onError = { e ->
                e.toDivaDatabaseException()
            }
        ) {
            block(driver)
        }
    }

    override suspend fun checkHealth(): Result<Boolean> {
        return tryResult(
            onError = { e ->
                e.toDivaDatabaseException()
            }
        ) {
            driver.execute(null, "SELECT 1", 0).value
            Result.success(true)
        }
    }

    override suspend fun close(): Result<Unit> {
        return tryResult(
            onError = { e ->
                e.toDivaDatabaseException()
            }
        ) {
            driver.close()
            Result.success(Unit)
        }
    }
}
