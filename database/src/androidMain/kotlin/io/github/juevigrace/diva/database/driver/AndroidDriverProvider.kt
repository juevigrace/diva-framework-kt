package io.github.juevigrace.diva.database.driver

import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.github.juevigrace.diva.types.DivaError
import io.github.juevigrace.diva.types.DivaResult

internal class AndroidDriverProvider(
    override val conf: AndroidConf,
) : DriverProviderBase<AndroidConf>(conf) {
    override suspend fun createDriver(schema: Schema): DivaResult<SqlDriver, DivaError> {
        return try {
            val syncSchema: SqlSchema<QueryResult.Value<Unit>> = when (schema) {
                is Schema.Sync -> schema.value
                is Schema.Async -> schema.value.synchronous()
            }
            DivaResult.success(
                AndroidSqliteDriver(
                    schema = syncSchema,
                    context = conf.context,
                    name = conf.driverConf.name,
                    callback = if (conf.driverConf.properties.containsKey("foreign_keys")) {
                        fkCallback(syncSchema)
                    } else {
                        object : AndroidSqliteDriver.Callback(syncSchema) {}
                    },
                ),
            )
        } catch (e: Exception) {
            DivaResult.failure(DivaError.database("CREATE", conf.driverConf.name, e.message, e))
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
