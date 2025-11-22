package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

interface DatabaseDriverProvider {
    val schema: SqlSchema<QueryResult.Value<Unit>>?
    val asyncSchema: SqlSchema<QueryResult.AsyncValue<Unit>>?
    val config: PlatformDriverConfig

    /**
     * Create a sql diver by the given schema and name synchronously.
     * */
    fun createDriver(): Result<SqlDriver>

    /**
     * Create a sql diver by the given schema and name asynchronously.
     * */
    fun createDriverAsync(): Result<SqlDriver>

    fun builder(): Builder

    interface Builder {
        fun setSchema(schema: SqlSchema<QueryResult.Value<Unit>>): Builder
        fun setSchemaAsync(schema: SqlSchema<QueryResult.AsyncValue<Unit>>): Builder
        fun setPlatform(platform: PlatformDriverConfig): Builder
        fun build(): Result<DatabaseDriverProvider>
    }
}
