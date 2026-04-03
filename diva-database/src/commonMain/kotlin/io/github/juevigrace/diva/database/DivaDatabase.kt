package io.github.juevigrace.diva.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.Option
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface DivaDatabase<S : TransacterBase> {
    suspend fun <T : Any> getOne(block: S.() -> Query<T>): Result<Option<T>>

    fun <T : Any> getOneAsFlow(
        ctx: CoroutineContext = EmptyCoroutineContext,
        block: S.() -> Query<T>,
    ): Flow<Result<Option<T>>>

    suspend fun <T : Any> getList(block: S.() -> Query<T>): Result<List<T>>

    fun <T : Any> getListAsFlow(
        ctx: CoroutineContext = EmptyCoroutineContext,
        block: S.() -> Query<T>,
    ): Flow<Result<List<T>>>

    suspend fun<T : Any> use(block: suspend S.() -> Result<T>): Result<T>

    suspend fun<T : Any> withDriver(block: suspend SqlDriver.() -> Result<T>): Result<T>

    suspend fun checkHealth(): Result<Boolean>

    suspend fun close(): Result<Unit>

    companion object {
        operator fun <S : TransacterBase> invoke(
            driver: SqlDriver,
            db: S
        ): DivaDatabase<S> {
            return DivaDatabaseImpl(
                driver = driver,
                db = db
            )
        }
    }
}
