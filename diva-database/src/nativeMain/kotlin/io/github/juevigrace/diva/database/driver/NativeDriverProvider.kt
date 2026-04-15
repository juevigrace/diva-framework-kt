package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.ConfigureDriverException
import io.github.juevigrace.diva.core.tryResult
import io.github.juevigrace.diva.core.util.logError
import io.github.juevigrace.diva.database.driver.configuration.NativeConf

internal class NativeDriverProvider(
    override val conf: NativeConf,
) : DriverProviderBase<NativeConf>(conf) {
    override fun createDriver(schema: Schema): Result<SqlDriver> {
        return tryResult(
            onError = { e ->
                val err = ConfigureDriverException(
                    details = Option.of("Failed to create driver with configuration: ${conf.driverConf}"),
                    cause = e
                )
                logError(err::class.simpleName ?: "ConfigureDriverException", err.message ?: err.toString())
                err
            }
        ) {
            val syncSchema: SqlSchema<QueryResult.Value<Unit>> = when (schema) {
                is Schema.Sync -> schema.value
                is Schema.Async -> schema.value.synchronous()
            }
            val driver: SqlDriver = NativeSqliteDriver(
                schema = syncSchema,
                name = conf.driverConf.name,
            )
            driver
        }
    }
}
