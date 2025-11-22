package io.github.juevigrace.diva.database.driver

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DatabaseDriverProviderImpl(
    private val context: Context,
    actual override val schema: SqlSchema<QueryResult.Value<Unit>>? = null,
    actual override val asyncSchema: SqlSchema<QueryResult.AsyncValue<Unit>>? = null,
    actual override val config: PlatformDriverConfig,
) : DatabaseDriverProvider {
    private val actualConfig: PlatformDriverConfig.Android = config as? PlatformDriverConfig.Android
        ?: error("PlatformDriverConfig must be Android")

    actual override fun createDriver(): Result<SqlDriver> {
        return try {
            if (schema == null && asyncSchema == null) error("No schema provided")
            Result.success(
                AndroidSqliteDriver(
                    schema = schema ?: asyncSchema!!.synchronous(),
                    context = context,
                    name = "jdbc:sqlite:${actualConfig.driverConfig.name}",
                    callback = fkCallback(schema ?: asyncSchema!!.synchronous()),
                ),
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    actual override fun createDriverAsync(): Result<SqlDriver> {
        return try {
            if (schema == null && asyncSchema == null) error("No schema provided")
            Result.success(
                AndroidSqliteDriver(
                    schema = asyncSchema?.synchronous() ?: schema!!,
                    context = context,
                    name = "jdbc:sqlite:${actualConfig.driverConfig.name}",
                    callback = fkCallback(asyncSchema?.synchronous() ?: schema!!),
                ),
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    actual override fun builder(): DatabaseDriverProvider.Builder {
        return Builder()
    }

    private fun fkCallback(schema: SqlSchema<QueryResult.Value<Unit>>): AndroidSqliteDriver.Callback {
        return object : AndroidSqliteDriver.Callback(schema) {
            override fun onOpen(db: SupportSQLiteDatabase) {
                db.setForeignKeyConstraintsEnabled(true)
                super.onOpen(db)
            }
        }
    }

    private class Builder : DatabaseDriverProvider.Builder {
        private lateinit var context: Context
        private var schema: SqlSchema<QueryResult.Value<Unit>>? = null
        private var asyncSchema: SqlSchema<QueryResult.AsyncValue<Unit>>? = null
        private lateinit var config: PlatformDriverConfig.Android

        fun setContext(context: Context): Builder {
            return apply { this.context = context }
        }

        override fun setSchema(schema: SqlSchema<QueryResult.Value<Unit>>): Builder {
            return apply { this.schema = schema }
        }

        override fun setSchemaAsync(schema: SqlSchema<QueryResult.AsyncValue<Unit>>): Builder {
            return apply { this.asyncSchema = schema }
        }

        override fun setPlatform(platform: PlatformDriverConfig): Builder {
            return when (platform) {
                is PlatformDriverConfig.Android -> {
                    apply { this.config = platform }
                }
                else -> {
                    apply {
                        this.config =
                            PlatformDriverConfig.Android(DriverConfig.SqliteDriverConfig("diva"))
                    }
                }
            }
        }

        override fun build(): Result<DatabaseDriverProvider> {
            try {
                if (!::context.isInitialized) error("Context not set")
                if (schema == null && asyncSchema == null) error("Schema not set")
                if (!::config.isInitialized) error("Platform not set")
                return Result.success(
                    DatabaseDriverProviderImpl(
                        context,
                        schema,
                        asyncSchema,
                        config,
                    ),
                )
            } catch (e: IllegalStateException) {
                return Result.failure(e)
            }
        }
    }
}
