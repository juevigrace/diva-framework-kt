package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import io.github.juevigrace.diva.types.DivaResult

actual class DriverProviderImpl(
    private val conf: PlatformDriverConf.Native,
) : DriverProvider {
    actual override suspend fun createDriver(schema: Schema): DivaResult<SqlDriver, Exception> {
        return try {
            val syncSchema: SqlSchema<QueryResult.Value<Unit>> = when (schema) {
                is Schema.Sync -> schema.value
                is Schema.Async -> schema.value.synchronous()
            }

            DivaResult.success(
                NativeSqliteDriver(
                    schema = syncSchema,
                    name = conf.driverConf.url,
                ),
            )
        } catch (e: Exception) {
            DivaResult.failure(e)
        }
    }
}
