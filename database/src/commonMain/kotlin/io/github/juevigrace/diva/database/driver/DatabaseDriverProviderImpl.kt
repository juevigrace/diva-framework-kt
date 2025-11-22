package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

expect class DatabaseDriverProviderImpl : DatabaseDriverProvider {
    override val schema: SqlSchema<QueryResult.Value<Unit>>?
    override val asyncSchema: SqlSchema<QueryResult.AsyncValue<Unit>>?
    override val config: PlatformDriverConfig

    override fun createDriver(): Result<SqlDriver>
    override fun createDriverAsync(): Result<SqlDriver>
    override fun builder(): DatabaseDriverProvider.Builder
}
