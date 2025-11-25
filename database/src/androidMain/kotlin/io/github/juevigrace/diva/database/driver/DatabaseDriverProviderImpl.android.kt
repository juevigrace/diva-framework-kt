package io.github.juevigrace.diva.database.driver

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.github.juevigrace.diva.types.DivaResult
import io.github.juevigrace.diva.types.isFailure

actual class DatabaseDriverProviderImpl(
    private val context: Context,
    private val conf: PlatformDriverConf.Android,
) : DatabaseDriverProvider {
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
                    name = "jdbc:sqlite:${conf.driverConfig.url}.db",
                    callback = if (conf.driverConfig.properties.containsKey("foreign_keys")) {
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

    actual class Builder : DatabaseDriverProvider.Builder {
        private lateinit var context: Context
        private var conf: DivaResult<PlatformDriverConf.Android, Exception> = DivaResult.failure(
            Exception("Platform configuration is not set")
        )

        fun setContext(context: Context): Builder {
            return apply { this.context = context }
        }

        actual override fun setPlatformConf(platformConf: PlatformDriverConf): Builder {
            return apply {
                this.conf = when (platformConf) {
                    is PlatformDriverConf.Android -> {
                        DivaResult.success(platformConf)
                    }
                    else -> {
                        DivaResult.failure(Exception("PlatformDriverConfig must be Android"))
                    }
                }
            }
        }

        actual override fun build(): DivaResult<DatabaseDriverProvider, Exception> {
            return try {
                if (!::context.isInitialized) error("Context not set")
                if (conf.isFailure()) error((conf as DivaResult.Failure).error)
                DivaResult.success(DatabaseDriverProviderImpl(context, (conf as DivaResult.Success).value))
            } catch (e: IllegalStateException) {
                DivaResult.failure(e)
            }
        }
    }
}
