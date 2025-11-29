package io.github.juevigrace.diva.database.driver

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.github.juevigrace.diva.types.DivaResult

actual class DriverProviderImpl(
    private val context: Context,
    private val conf: PlatformDriverConf.Android,
) : DriverProvider {
    actual override suspend fun createDriver(schema: Schema): DivaResult<SqlDriver, Exception> {
        return try {
            val syncSchema: SqlSchema<QueryResult.Value<Unit>> = when (schema) {
                is Schema.Sync -> schema.value
                is Schema.Async -> schema.value.synchronous()
            }
            DivaResult.success(
                AndroidSqliteDriver(
                    schema = syncSchema,
                    context = context,
                    name = conf.driverConf.url,
                    callback = if (conf.driverConf.properties.containsKey("foreign_keys")) {
                        fkCallback(syncSchema)
                    } else {
                        object : AndroidSqliteDriver.Callback(syncSchema) {}
                    },
                ),
            )
        } catch (e: Exception) {
            DivaResult.failure(e)
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
