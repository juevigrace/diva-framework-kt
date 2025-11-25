package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import io.github.juevigrace.diva.types.DivaResult
import io.github.juevigrace.diva.types.isFailure

actual class DriverProviderImpl(
    private val conf: PlatformDriverConf.Native,
) : DriverProvider {
    actual override suspend fun createDriver(schema: Schema): DivaResult<SqlDriver, Exception> {
        return try {
            val syncSchema: SqlSchema<QueryResult.Value<Unit>> = when (schema) {
                is Schema.Sync -> schema.value
                is Schema.Async -> schema.value.synchronous()
            }

            DivaResult.success(
                NativeSqliteDriver(
                    schema = syncSchema,
                    name = conf.driverConf.url,
                ),
            )
        } catch (e: Exception) {
            DivaResult.failure(e)
        }
    }

    actual class Builder : DriverProvider.Builder {
        private var conf: DivaResult<PlatformDriverConf.Native, Exception> = DivaResult.failure(
            Exception("Platform configuration is not set")
        )

        actual override fun setPlatformConf(platformConf: PlatformDriverConf): Builder {
            return apply {
                when (platformConf) {
                    is PlatformDriverConf.Native -> {
                        this.conf = DivaResult.success(platformConf)
                    }
                    else -> {
                        this.conf = DivaResult.failure(Exception("PlatformDriverConfig must be Native"))
                    }
                }
            }
        }

        actual override fun build(): DivaResult<DriverProvider, Exception> {
            try {
                if (conf.isFailure()) throw (conf as DivaResult.Failure).error

                return DivaResult.success(DriverProviderImpl((conf as DivaResult.Success).value))
            } catch (e: IllegalStateException) {
                return DivaResult.failure(e)
            }
        }
    }
}
