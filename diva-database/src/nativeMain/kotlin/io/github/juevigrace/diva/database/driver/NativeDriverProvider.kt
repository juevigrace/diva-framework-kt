package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.toDatabaseError
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.tryResult
import io.github.juevigrace.diva.database.driver.configuration.NativeConf

internal class NativeDriverProvider(
    override val conf: NativeConf,
) : DriverProviderBase<NativeConf>(conf) {
    override fun createDriver(schema: Schema): DivaResult<SqlDriver, DivaError.DatabaseError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().toDatabaseError(DatabaseAction.D_DRIVER)
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
            DivaResult.success(driver)
        }
    }
}
