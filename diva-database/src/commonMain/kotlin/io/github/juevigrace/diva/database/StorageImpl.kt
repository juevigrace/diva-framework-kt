package io.github.juevigrace.diva.database

import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.types.DivaError
import io.github.juevigrace.diva.core.types.DivaResult
import io.github.juevigrace.diva.database.driver.DriverProvider
import io.github.juevigrace.diva.database.driver.Schema
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class StorageImpl<S : TransacterBase> : Storage<S> {
    private val cScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    private lateinit var driver: SqlDriver
    private lateinit var scope: StorageScope<S>

    constructor(
        provider: DriverProvider,
        schema: Schema,
        onDriverCreated: (SqlDriver) -> S,
        onError: (DivaResult<Nothing, DivaError>) -> Unit
    ) {
        cScope.launch {
            when (val result: DivaResult<SqlDriver, DivaError> = provider.createDriver(schema)) {
                is DivaResult.Failure -> {
                    onError(result)
                }
                is DivaResult.Success -> {
                    driver = result.value
                    scope = StorageScope(onDriverCreated(driver))
                }
            }
        }
    }

    override suspend fun <T : Any> withDb(
        block: suspend StorageScope<S>.() -> DivaResult<T, DivaError>
    ): DivaResult<T, DivaError> {
        return try {
            block(scope)
        } catch (e: Exception) {
            DivaResult.failure(
                DivaError.database(
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
