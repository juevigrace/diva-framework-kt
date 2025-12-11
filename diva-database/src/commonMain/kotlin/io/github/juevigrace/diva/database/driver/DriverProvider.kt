package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult

interface DriverProvider {
    /**
     * Create a sql driver by the given schema and name synchronously.
     */
    suspend fun createDriver(schema: Schema): DivaResult<SqlDriver, DivaError>
}

internal abstract class DriverProviderBase<C : PlatformDriverConf>(
    protected open val conf: C
) : DriverProvider
