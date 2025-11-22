package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

actual class DatabaseDriverProviderImpl(
    actual override val schema: SqlSchema<QueryResult.Value<Unit>>?,
    actual override val asyncSchema: SqlSchema<QueryResult.AsyncValue<Unit>>?,
    actual override val config: PlatformDriverConfig
) : DatabaseDriverProvider {
    actual override fun createDriver(): Result<SqlDriver> {
        TODO("Not yet implemented")
    }

    actual override fun createDriverAsync(): Result<SqlDriver> {
        TODO("Not yet implemented")
    }

    actual override fun builder(): DatabaseDriverProvider.Builder {
        return Builder()
    }

    private class Builder : DatabaseDriverProvider.Builder {
        private var schema: SqlSchema<QueryResult.Value<Unit>>? = null
        private var asyncSchema: SqlSchema<QueryResult.AsyncValue<Unit>>? = null
        private lateinit var config: PlatformDriverConfig.Jvm

        override fun setSchema(schema: SqlSchema<QueryResult.Value<Unit>>): Builder {
            return apply { this.schema = schema }
        }

        override fun setSchemaAsync(schema: SqlSchema<QueryResult.AsyncValue<Unit>>): Builder {
            return apply { this.asyncSchema = schema }
        }

        override fun setPlatform(platform: PlatformDriverConfig): Builder {
            return when (platform) {
                is PlatformDriverConfig.Jvm -> {
                    apply { this.config = platform }
                }
                else -> {
                    apply {
                        this.config =
                            PlatformDriverConfig.Jvm(DriverConfig.SqliteDriverConfig("diva"))
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