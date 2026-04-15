package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.ConfigureDriverException
import io.github.juevigrace.diva.core.map
import io.github.juevigrace.diva.core.tryResult
import io.github.juevigrace.diva.core.util.logError
import io.github.juevigrace.diva.database.driver.configuration.DriverConf
import io.github.juevigrace.diva.database.driver.configuration.JvmConf
import kotlinx.coroutines.runBlocking

internal class JvmDriverProvider(
    override val conf: JvmConf
) : DriverProviderBase<JvmConf>(conf) {
    override fun createDriver(schema: Schema): Result<SqlDriver> {
        return tryResult(
            onError = { e ->
                val err = e as? ConfigureDriverException
                    ?: ConfigureDriverException(
                        details = Option.of("Failed to create driver with configuration: ${conf.driverConf}"),
                        cause = e
                    )
                logError(err::class.simpleName ?: "ConfigureDriverException", err.message ?: err.toString())
                err
            }
        ) {
            val driver: SqlDriver = createDriverFromDataSource().getOrElse { err ->
                throw err
            }
            runBlocking {
                when (schema) {
                    is Schema.Sync -> schema.value.create(driver)
                    is Schema.Async -> schema.value.synchronous().create(driver)
                }
                driver
            }
        }
    }

    private fun createDriverFromDataSource(): Result<SqlDriver> {
        return tryResult(
            onError = { e ->
                val err = ConfigureDriverException(
                    details = Option.of("Failed to create driver with configuration: ${conf.driverConf}"),
                    cause = e
                )
                logError(err::class.simpleName ?: "ConfigureDriverException", err.message ?: err.toString())
                err
            }
        ) {
            return when (conf.driverConf) {
                is DriverConf.SqliteDriverConf -> {
                    Result.success(
                        JdbcSqliteDriver(
                            url = "jdbc:sqlite:${conf.driverConf.name}",
                            properties = conf.driverConf.properties.toProperties()
                        )
                    )
                }
                is DriverConf.MysqlDriverConf -> {
                    val config = createHikariConfig(
                        "jdbc:mysql://${conf.driverConf.host}:${conf.driverConf.port}/${conf.driverConf.database}",
                        conf.driverConf.username,
                        conf.driverConf.password,
                    )
                    Result.success(HikariDataSource(config).asJdbcDriver())
                }
                is DriverConf.PostgresqlDriverConf -> {
                    val config = createHikariConfig(
                        "jdbc:postgresql://${conf.driverConf.host}:${conf.driverConf.port}/${conf.driverConf.database}",
                        conf.driverConf.username,
                        conf.driverConf.password,
                    )
                    Result.success(HikariDataSource(config).asJdbcDriver())
                }
                is DriverConf.H2DriverConf -> {
                    val config = createHikariConfig(
                        conf.driverConf.url,
                        conf.driverConf.username,
                        conf.driverConf.password,
                    )
                    Result.success(HikariDataSource(config).asJdbcDriver())
                }
            }
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
