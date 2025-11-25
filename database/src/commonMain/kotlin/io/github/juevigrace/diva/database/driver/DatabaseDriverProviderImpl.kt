package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.types.DivaResult

expect class DatabaseDriverProviderImpl : DatabaseDriverProvider {
    override suspend fun createDriver(schema: Schema): DivaResult<SqlDriver, Exception>

    class Builder : DatabaseDriverProvider.Builder {
        override fun setPlatformConf(platformConf: PlatformDriverConf): Builder
        override fun build(): DivaResult<DatabaseDriverProvider, Exception>
    }
}
