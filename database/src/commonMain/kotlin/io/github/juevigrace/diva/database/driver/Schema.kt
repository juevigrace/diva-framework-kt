package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlSchema

sealed interface Schema {
    data class Sync(val value: SqlSchema<QueryResult.Value<Unit>>) : Schema
    data class Async(val value: SqlSchema<QueryResult.AsyncValue<Unit>>) : Schema
}
