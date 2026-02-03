package io.github.juevigrace.diva.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import io.github.juevigrace.diva.core.errors.toDivaError
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface DivaDatabase<S : TransacterBase> {
    suspend fun <T : Any> getOne(
        onError: (Exception) -> DivaError = { e ->
            e.toDivaError(
                ErrorCause.Error.Ex(
                    ex = e,
                    details = Option.Some("db action = ${{DatabaseAction.D_GET_ONE.name}}")
                )
            )
        },
        block: S.() -> Query<T>
    ): DivaResult<Option<T>, DivaError>

    fun <T : Any> getOneAsFlow(
        ctx: CoroutineContext = EmptyCoroutineContext,
        onError: (Exception) -> DivaError = { e ->
            e.toDivaError(
                ErrorCause.Error.Ex(
                    ex = e,
                    details = Option.Some("db action = ${DatabaseAction.D_GET_ONE.name}")
                )
            )
        },
        block: S.() -> Query<T>,
    ): Flow<DivaResult<Option<T>, DivaError>>

    suspend fun <T : Any> getList(
        onError: (Exception) -> DivaError = { e ->
            e.toDivaError(
                ErrorCause.Error.Ex(
                    ex = e,
                    details = Option.Some("db action = ${DatabaseAction.D_GET_LIST.name}")
                )
            )
        },
        block: S.() -> Query<T>
    ): DivaResult<List<T>, DivaError>

    fun <T : Any> getListAsFlow(
        ctx: CoroutineContext = EmptyCoroutineContext,
        onError: (Exception) -> DivaError = { e ->
            e.toDivaError(
                ErrorCause.Error.Ex(
                    ex = e,
                    details = Option.Some("db action = ${DatabaseAction.D_GET_LIST.name}")
                )
            )
        },
        block: S.() -> Query<T>,
    ): Flow<DivaResult<List<T>, DivaError>>

    suspend fun<T : Any> use(
        onError: (Exception) -> DivaError = { e ->
            e.toDivaError(
                ErrorCause.Error.Ex(
                    ex = e,
                    details = Option.Some("db action = ${DatabaseAction.D_USE.name}")
                )
            )
        },
        block: suspend S.() -> DivaResult<T, DivaError>
    ): DivaResult<T, DivaError>

    suspend fun<T : Any> withDriver(
        onError: (Exception) -> DivaError = { e ->
            e.toDivaError(
                ErrorCause.Error.Ex(
                    ex = e,
                    details = Option.Some("db action = ${DatabaseAction.D_DRIVER}")
                )
            )
        },
        block: suspend SqlDriver.() -> DivaResult<T, DivaError>
    ): DivaResult<T, DivaError>

    suspend fun checkHealth(
        onError: (Exception) -> DivaError = { e ->
            e.toDivaError(
                ErrorCause.Error.Ex(
                    ex = e,
                    details = Option.Some("db action = ${DatabaseAction.D_DRIVER}")
                )
            )
        }
    ): DivaResult<Boolean, DivaError>

    suspend fun close(
        onError: (Exception) -> DivaError = { e ->
            e.toDivaError(
                ErrorCause.Error.Ex(
                    ex = e,
                    details = Option.Some("db action = ${DatabaseAction.D_DRIVER}")
                )
            )
        }
    ): DivaResult<Unit, DivaError>

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
