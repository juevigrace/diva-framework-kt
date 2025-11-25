package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import io.github.juevigrace.diva.types.DivaResult
import io.github.juevigrace.diva.types.Option

actual class DatabaseDriverProviderImpl :
    DatabaseDriverProvider,
    DatabaseSchemaProvider() {
    actual override val schema: Schema
        get() = TODO("Not yet implemented")
    actual override val conf: PlatformDriverConf
        get() = TODO("Not yet implemented")

    actual override suspend fun createDriver(): DivaResult<SqlDriver, Exception> {
        TODO("Not yet implemented")
    }

    actual override fun getSchemaSync(): Option<SqlSchema<QueryResult.Value<Unit>>> {
        TODO("Not yet implemented")
    }

    actual override fun builder(): DatabaseDriverProvider.Builder {
        TODO("Not yet implemented")
    }

    actual class Builder :
        DatabaseDriverProvider.Builder {
        actual override fun setSchema(schema: Schema): Builder {
            TODO("Not yet implemented")
        }

        actual override fun setPlatformConf(platformConf: PlatformDriverConf): Builder {
            TODO("Not yet implemented")
        }

        actual override fun build(): DivaResult<DatabaseDriverProvider, Exception> {
            TODO("Not yet implemented")
        }
    }
}