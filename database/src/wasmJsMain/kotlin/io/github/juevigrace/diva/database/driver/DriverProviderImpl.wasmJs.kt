package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.types.DivaResult

actual class DriverProviderImpl(
    private val conf: PlatformDriverConf.Web,
) : DriverProvider{
    actual override suspend fun createDriver(schema: Schema): DivaResult<SqlDriver, Exception> {
        TODO("Not yet implemented")
    }
}