package io.github.juevigrace.diva.database.driver

import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.toDatabaseError
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.tryResult
import io.github.juevigrace.diva.database.driver.configuration.AndroidConf

internal class AndroidDriverProvider(
    override val conf: AndroidConf,
) : DriverProviderBase<AndroidConf>(conf) {
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
            val driver: SqlDriver = AndroidSqliteDriver(
                schema = syncSchema,
                context = conf.context,
                name = conf.driverConf.name,
                callback = if (conf.driverConf.properties.containsKey("foreign_keys")) {
                    fkCallback(syncSchema)
                } else {
                    object : AndroidSqliteDriver.Callback(syncSchema) {}
                },
            )
            DivaResult.success(driver)
        }
    }

    private fun fkCallback(schema: SqlSchema<QueryResult.Value<Unit>>): AndroidSqliteDriver.Callback {
        return object : AndroidSqliteDriver.Callback(schema) {
            override fun onOpen(db: SupportSQLiteDatabase) {
                db.setForeignKeyConstraintsEnabled(true)
                super.onOpen(db)
            }
        }
    }
}
