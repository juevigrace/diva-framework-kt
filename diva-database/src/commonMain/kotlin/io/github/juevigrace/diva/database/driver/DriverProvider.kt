package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult

interface DriverProvider {
    /**
     * Create a sql driver by the given schema and name synchronously.
     */
    fun createDriver(schema: Schema): DivaResult<SqlDriver, DivaError>
}
