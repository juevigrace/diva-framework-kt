package io.github.juevigrace.diva.database

import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.types.DivaError
import io.github.juevigrace.diva.types.DivaResult

interface Storage<S : TransacterBase> {
    /**
     * Primary entry point for all database operations.
     * All database functions must be used within this scope.
     */
    suspend fun <T : Any> withDb(
        block: suspend StorageScope<S>.() -> DivaResult<T, DivaError>
    ): DivaResult<T, DivaError>

    /**
     * System operations - available outside withDb scope
     */
    suspend fun checkHealth(): DivaResult<Boolean, DivaError>

    suspend fun close(): DivaResult<Unit, DivaError>

    companion object {
        operator fun <S : TransacterBase> invoke(
            driver: SqlDriver,
            db: S
        ): Storage<S> = StorageImpl(
            driver = driver,
            db = db
        )
    }
}

