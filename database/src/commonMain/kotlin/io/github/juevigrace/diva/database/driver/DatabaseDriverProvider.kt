package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.types.DivaResult

interface DatabaseDriverProvider {
    /**
     * Create a sql diver by the given schema and name synchronously.
     * */
    suspend fun createDriver(schema: Schema): DivaResult<SqlDriver, Exception>

    interface Builder {
        // todo: add driver config as a separate function?
        fun setPlatformConf(platformConf: PlatformDriverConf): Builder
        fun build(): DivaResult<DatabaseDriverProvider, Exception>
    }
}
