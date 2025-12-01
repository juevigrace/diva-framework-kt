package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.types.DivaError
import io.github.juevigrace.diva.types.DivaResult

expect class DriverProviderImpl : DriverProvider {
    override suspend fun createDriver(schema: Schema): DivaResult<SqlDriver, DivaError>
}
