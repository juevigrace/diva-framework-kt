package io.github.juevigrace.diva.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface DivaDatabase<S : TransacterBase> {
    suspend fun <T : Any> getOne(block: S.() -> Query<T>): DivaResult<Option<T>, DivaError.DatabaseError>

    fun <T : Any> getOneAsFlow(
        ctx: CoroutineContext = EmptyCoroutineContext,
        block: S.() -> Query<T>,
    ): Flow<DivaResult<Option<T>, DivaError.DatabaseError>>

    suspend fun <T : Any> getList(block: S.() -> Query<T>): DivaResult<List<T>, DivaError.DatabaseError>

    fun <T : Any> getListAsFlow(
        ctx: CoroutineContext = EmptyCoroutineContext,
        block: S.() -> Query<T>,
    ): Flow<DivaResult<List<T>, DivaError.DatabaseError>>

    suspend fun<T : Any> use(
        block: suspend S.() -> DivaResult<T, DivaError.DatabaseError>
    ): DivaResult<T, DivaError.DatabaseError>

    suspend fun<T : Any> withDriver(
        block: suspend SqlDriver.() -> DivaResult<T, DivaError.DatabaseError>
    ): DivaResult<T, DivaError.DatabaseError>

    suspend fun checkHealth(): DivaResult<Boolean, DivaError.DatabaseError>

    suspend fun close(): DivaResult<Unit, DivaError.DatabaseError>

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
