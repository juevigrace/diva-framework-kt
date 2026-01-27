package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.asDatabaseError
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.map
import io.github.juevigrace.diva.core.tryResult
import io.github.juevigrace.diva.database.driver.configuration.DriverConf
import io.github.juevigrace.diva.database.driver.configuration.JvmConf
import kotlinx.coroutines.runBlocking

internal class JvmDriverProvider(
    override val conf: JvmConf
) : DriverProviderBase<JvmConf>(conf) {
    override fun createDriver(schema: Schema): DivaResult<SqlDriver, DivaError.DatabaseError> {
        return tryResult(
            onError = { e ->
                e.toDivaError("DriverProvider.createDriver").asDatabaseError(DatabaseAction.START)
            }
        ) {
            createDriverFromDataSource().map { driver ->
                runBlocking {
                    when (schema) {
                        is Schema.Sync -> schema.value.create(driver)
                        is Schema.Async -> schema.value.synchronous().create(driver)
                    }
                    driver
                }
            }
        }
    }

    private fun createDriverFromDataSource(): DivaResult<SqlDriver, DivaError.DatabaseError> {
        return tryResult(
            onError = { e ->
                e.toDivaError("DriverProvider.createDriverFromDataSource").asDatabaseError(DatabaseAction.CONFIGURE)
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
            val driver: SqlDriver = HikariDataSource(config).asJdbcDriver()
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
