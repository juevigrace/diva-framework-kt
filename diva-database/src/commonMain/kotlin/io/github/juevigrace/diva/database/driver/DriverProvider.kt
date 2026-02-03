package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError

// TODO: improve driver callbacks
interface DriverProvider {
    /**
     * Create a sql driver by the given schema and name synchronously.
     */
    fun createDriver(schema: Schema): DivaResult<SqlDriver, DivaError>
}
