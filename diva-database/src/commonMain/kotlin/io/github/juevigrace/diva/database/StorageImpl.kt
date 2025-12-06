package io.github.juevigrace.diva.database

import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.types.DivaError
import io.github.juevigrace.diva.core.types.DivaResult

internal class StorageImpl<S : TransacterBase>(
    private val driver: SqlDriver,
    db: S,
) : Storage<S> {
    private val scope: StorageScope<S> = StorageScopeImpl(db)

    override suspend fun <T : Any> withDb(
        block: suspend StorageScope<S>.() -> DivaResult<T, DivaError>
    ): DivaResult<T, DivaError> {
        return try {
            block(scope)
        } catch (e: Exception) {
            DivaResult.failure(
                DivaError.Companion.database(
                    operation = "WITH_DB",
                    table = null,
                    details = e.message,
                    cause = e
                )
            )
        }
    }

    override suspend fun checkHealth(): DivaResult<Boolean, DivaError> {
        return try {
            driver.execute(null, "SELECT 1", 0).value
            DivaResult.success(true)
        } catch (e: Exception) {
            DivaResult.failure(DivaError.database("CHECK_CONNECTION", null, e.message, e))
        }
    }

    override suspend fun close(): DivaResult<Unit, DivaError> {
        return try {
            driver.close()
            DivaResult.success(Unit)
        } catch (e: Exception) {
            DivaResult.failure(DivaError.database("CLOSE", null, e.message, e))
        }
    }
}
