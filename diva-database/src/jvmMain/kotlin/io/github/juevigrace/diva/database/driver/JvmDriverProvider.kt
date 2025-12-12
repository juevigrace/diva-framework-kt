package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.JdbcDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.flatMap
import io.github.juevigrace.diva.core.models.tryResult
import io.github.juevigrace.diva.database.driver.configuration.DriverConf
import io.github.juevigrace.diva.database.driver.configuration.JvmConf
import kotlinx.coroutines.runBlocking

internal class JvmDriverProvider(
    override val conf: JvmConf
) : DriverProviderBase<JvmConf>(conf) {
    override fun createDriver(schema: Schema): DivaResult<SqlDriver, DivaError> {
        return tryResult(
            onError = { e ->
                DivaError.exception(
                    e = e,
                    origin = "JvmDriverProvider.createDriver",
                )
            }
        ) {
            val syncSchema: SqlSchema<QueryResult.Value<Unit>> = when (schema) {
                is Schema.Sync -> schema.value
                is Schema.Async -> schema.value.synchronous()
            }
            createDriverFromDataSource().flatMap { driver ->
                runBlocking { syncSchema.awaitCreate(driver) }
                DivaResult.success(driver)
            }
        }
    }

    private fun createDriverFromDataSource(): DivaResult<SqlDriver, DivaError> {
        return tryResult(
            onError = { e ->
                DivaError.exception(
                    e = e,
                    origin = "JvmDriverProvider.createDriverFromDataSource",
                )
            }
        ) {
            val config: HikariConfig = when (conf.driverConf) {
                is DriverConf.SqliteDriverConf -> {
                    createHikariConfig(
                        url = "jdbc:sqlite:${conf.driverConf.name}",
                        username = "",
                        password = ""
                    )
                }
                is DriverConf.MysqlDriverConf -> {
                    createHikariConfig(
                        "jdbc:mysql://${conf.driverConf.host}:${conf.driverConf.port}/${conf.driverConf.database}",
                        conf.driverConf.username,
                        conf.driverConf.password,
                    )
                }
                is DriverConf.PostgresqlDriverConf -> {
                    createHikariConfig(
                        "jdbc:postgresql://${conf.driverConf.host}:${conf.driverConf.port}/${conf.driverConf.database}",
                        conf.driverConf.username,
                        conf.driverConf.password,
                    )
                }
                is DriverConf.H2DriverConf -> {
                    createHikariConfig(
                        conf.driverConf.url,
                        conf.driverConf.username,
                        conf.driverConf.password,
                    )
                }
            }
            val driver: JdbcDriver = HikariDataSource(config).asJdbcDriver()
            DivaResult.success(driver)
        }
    }

    private fun createHikariConfig(
        url: String,
        username: String,
        password: String,
    ): HikariConfig =
        HikariConfig().apply {
            jdbcUrl = url
            this.username = username
            this.password = password
            maximumPoolSize = MAX_POOL_SIZE
            minimumIdle = MIN_IDLE_CONNECTIONS
        }

    companion object {
        const val MAX_POOL_SIZE = 10
        const val MIN_IDLE_CONNECTIONS = 2
    }
}
