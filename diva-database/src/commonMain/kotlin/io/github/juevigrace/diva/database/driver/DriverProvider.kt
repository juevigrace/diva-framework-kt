package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.db.SqlDriver

interface DriverProvider {
    /**
     * Create a SQL driver by the given schema and name synchronously.
     */
    fun createDriver(schema: Schema): Result<SqlDriver>
}
