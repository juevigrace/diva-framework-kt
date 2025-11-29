package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.JdbcDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.juevigrace.diva.types.DivaResult

actual class DriverProviderImpl(
    private val conf: PlatformDriverConf.Jvm,
) : DriverProvider {
    actual override suspend fun createDriver(schema: Schema): DivaResult<SqlDriver, Exception> {
        return try {
            val sync: SqlSchema<QueryResult.Value<Unit>> = when (schema) {
                is Schema.Sync -> schema.value
                is Schema.Async -> schema.value.synchronous()
            }
            when (val result: DivaResult<SqlDriver, Exception> = createDriverFromDataSource(sync)) {
                is DivaResult.Failure -> result
                is DivaResult.Success -> {
                    sync.awaitCreate(result.value)
                    DivaResult.success(result.value)
                }
            }
        } catch (e: Exception) {
            DivaResult.failure(e)
        }
    }

    private fun createDriverFromDataSource(
        schema: SqlSchema<QueryResult.Value<Unit>>
    ): DivaResult<SqlDriver, Exception> {
        return try {
            val driver: JdbcDriver = when (conf.driverConf) {
                is DriverConf.MysqlDriverConf -> {
                    createDataSourceWithConfig(
                        driver = MYSQL_DRIVER_NAME,
                        host = conf.driverConf.host,
                        port = conf.driverConf.port,
                        database = conf.driverConf.database,
                        username = conf.driverConf.username,
                        password = conf.driverConf.password
                    ).asJdbcDriver()
                }
                is DriverConf.PostgresqlDriverConf -> {
                    createDataSourceWithConfig(
                        driver = POSTGRESQL_DRIVER_NAME,
                        host = conf.driverConf.host,
                        port = conf.driverConf.port,
                        database = conf.driverConf.database,
                        username = conf.driverConf.username,
                        password = conf.driverConf.password
                    ).asJdbcDriver()
                }
                is DriverConf.H2DriverConf -> {
                    createDataSourceWithConfig(
                        url = conf.driverConf.url,
                        username = conf.driverConf.username,
                        password = conf.driverConf.password
                    ).asJdbcDriver()
                }
                is DriverConf.SqliteDriverConf -> {
                    JdbcSqliteDriver(
                        conf.driverConf.url,
                        conf.driverConf.properties.toProperties(),
                        schema,
                    )
                }
            }
            DivaResult.success(driver)
        } catch (e: Exception) {
            return DivaResult.failure(e)
        }
    }

    private fun createDataSourceWithConfig(
        url: String,
        username: String,
        password: String,
    ): HikariDataSource {
        val config: HikariConfig = HikariConfig().apply {
            jdbcUrl = url
            this.username = username
            this.password = password
            maximumPoolSize = MAX_POOL_SIZE
            minimumIdle = MIN_IDLE_CONNECTIONS
        }
        return HikariDataSource(config)
    }

    private fun createDataSourceWithConfig(
        driver: String,
        username: String,
        password: String,
        host: String,
        port: Int,
        database: String,
    ): HikariDataSource {
        val config: HikariConfig = HikariConfig().apply {
            jdbcUrl = "jdbc:$driver://$host:$port/$database"
            this.username = username
            this.password = password
            maximumPoolSize = MAX_POOL_SIZE
            minimumIdle = MIN_IDLE_CONNECTIONS
        }
        return HikariDataSource(config)
    }

    companion object {
        const val MYSQL_DRIVER_NAME = "mysql"
        const val POSTGRESQL_DRIVER_NAME = "postgresql"
        const val MAX_POOL_SIZE = 10
        const val MIN_IDLE_CONNECTIONS = 2
    }
}
