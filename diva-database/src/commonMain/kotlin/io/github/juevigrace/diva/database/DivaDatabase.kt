package io.github.juevigrace.diva.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface DivaDatabase<S : TransacterBase> {
    suspend fun <T : Any> getOne(block: S.() -> Query<T>): DivaResult<Option<T>, DivaError>

    fun <T : Any> getOneAsFlow(
        ctx: CoroutineContext = EmptyCoroutineContext,
        block: S.() -> Query<T>,
    ): Flow<DivaResult<Option<T>, DivaError>>

    suspend fun <T : Any> getList(block: S.() -> Query<T>): DivaResult<List<T>, DivaError>

    fun <T : Any> getListAsFlow(
        ctx: CoroutineContext = EmptyCoroutineContext,
        block: S.() -> Query<T>,
    ): Flow<DivaResult<List<T>, DivaError>>

    suspend fun<T : Any> use(block: suspend S.() -> DivaResult<T, DivaError>): DivaResult<T, DivaError>

    suspend fun checkHealth(): DivaResult<Boolean, DivaError>

    suspend fun close(): DivaResult<Unit, DivaError>

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
