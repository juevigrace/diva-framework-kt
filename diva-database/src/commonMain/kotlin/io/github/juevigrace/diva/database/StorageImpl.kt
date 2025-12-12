package io.github.juevigrace.diva.database

import app.cash.sqldelight.TransacterBase
import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.fold
import io.github.juevigrace.diva.core.models.tryResult
import io.github.juevigrace.diva.database.driver.DriverProvider
import io.github.juevigrace.diva.database.driver.Schema

internal class StorageImpl<S : TransacterBase> : Storage<S> {
    private var driverErr: DivaError? = null
    private lateinit var driver: SqlDriver
    private lateinit var scope: StorageScope<S>

    constructor(
        provider: DriverProvider,
        schema: Schema,
        onDriverCreated: (SqlDriver) -> S,
    ) {
        provider.createDriver(schema).fold(
            onFailure = { err ->
                driverErr = err
            },
            onSuccess = { sqlDriver ->
                driver = sqlDriver
                scope = StorageScope(onDriverCreated(driver))
            }
        )
    }

    override suspend fun <T : Any> withDb(
        block: suspend StorageScope<S>.() -> DivaResult<T, DivaError>
    ): DivaResult<T, DivaError> {
        if (driverErr != null) {
            return DivaResult.failure(driverErr!!)
        }
        return tryResult(
            onError = { e ->
                DivaError.exception(
                    e = e,
                    origin = "Storage.withDb"
                )
            }
        ) {
            block(scope)
        }
    }

    override suspend fun checkHealth(): DivaResult<Boolean, DivaError> {
        if (driverErr != null) {
            return DivaResult.failure(driverErr!!)
        }
        return tryResult(
            onError = { e ->
                DivaError.exception(
                    e = e,
                    origin = "Storage.checkHealth"
                )
            }
        ) {
            driver.execute(null, "SELECT 1", 0).value
            DivaResult.success(true)
        }
    }

    override suspend fun close(): DivaResult<Unit, DivaError> {
        if (driverErr != null) {
            return DivaResult.failure(driverErr!!)
        }
        return tryResult(
            onError = { e ->
                DivaError.exception(
                    e = e,
                    origin = "Storage.close"
                )
            }
        ) {
            driver.close()
            DivaResult.success(Unit)
        }
    }
}
