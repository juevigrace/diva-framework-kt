package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult

internal class NativeDriverProvider(
    override val conf: NativeConf,
) : DriverProviderBase<NativeConf>(conf) {
    override suspend fun createDriver(schema: Schema): DivaResult<SqlDriver, DivaError> {
        return try {
            val syncSchema: SqlSchema<QueryResult.Value<Unit>> =
                when (schema) {
                    is Schema.Sync -> schema.value
                    is Schema.Async -> schema.value.synchronous()
                }

            DivaResult.success(
                NativeSqliteDriver(
                    schema = syncSchema,
                    name = conf.driverConf.name,
                ),
            )
        } catch (e: Exception) {
            DivaResult.failure(DivaError.database("CREATE", conf.driverConf.name, e.message, e))
        }
    }
}
