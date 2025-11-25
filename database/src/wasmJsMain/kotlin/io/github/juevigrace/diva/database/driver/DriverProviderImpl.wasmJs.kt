package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.types.DivaResult

actual class DriverProviderImpl(
    private val conf: PlatformDriverConf.Web,
) : DriverProvider{
    actual override suspend fun createDriver(schema: Schema): DivaResult<SqlDriver, Exception> {
        TODO("Not yet implemented")
    }

    actual class Builder : DriverProvider.Builder {
        actual override fun setPlatformConf(platformConf: PlatformDriverConf): Builder {
            TODO("Not yet implemented")
        }

        actual override fun build(): DivaResult<DriverProvider, Exception> {
            TODO("Not yet implemented")
        }
    }
}