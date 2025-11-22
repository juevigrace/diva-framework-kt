package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DatabaseDriverProviderImpl(
    actual override val schema: SqlSchema<QueryResult.Value<Unit>>?,
    actual override val asyncSchema: SqlSchema<QueryResult.AsyncValue<Unit>>?,
    actual override val config: PlatformDriverConfig,
) : DatabaseDriverProvider {
    private val actualConfig: PlatformDriverConfig.Native = config as? PlatformDriverConfig.Native
        ?: error("PlatformDriverConfig must be Native")

    actual override fun createDriver(): Result<SqlDriver> {
        return try {
            if (schema == null && asyncSchema == null) error("No schema provided")
            Result.success(
                NativeSqliteDriver(
                    schema = schema ?: asyncSchema!!.synchronous(),
                    name = actualConfig.driverConfig.name,
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
                NativeSqliteDriver(
                    schema = asyncSchema?.synchronous() ?: schema!!,
                    name = actualConfig.driverConfig.name,
                ),
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    actual override fun builder(): DatabaseDriverProvider.Builder {
        return Builder()
    }

    private class Builder : DatabaseDriverProvider.Builder {
        private var schema: SqlSchema<QueryResult.Value<Unit>>? = null
        private var asyncSchema: SqlSchema<QueryResult.AsyncValue<Unit>>? = null
        private lateinit var config: PlatformDriverConfig.Native

        override fun setSchema(schema: SqlSchema<QueryResult.Value<Unit>>): Builder {
            return apply { this.schema = schema }
        }

        override fun setSchemaAsync(schema: SqlSchema<QueryResult.AsyncValue<Unit>>): Builder {
            return apply { this.asyncSchema = schema }
        }

        override fun setPlatform(platform: PlatformDriverConfig): Builder {
            return when (platform) {
                is PlatformDriverConfig.Native -> {
                    apply { this.config = platform }
                }

                else -> {
                    apply {
                        this.config =
                            PlatformDriverConfig.Native(DriverConfig.SqliteDriverConfig("diva"))
                    }
                }
            }
        }

        override fun build(): Result<DatabaseDriverProvider> {
            try {
                if (schema == null && asyncSchema == null) error("Schema not set")
                if (!::config.isInitialized) error("Platform not set")
                return Result.success(
                    DatabaseDriverProviderImpl(
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