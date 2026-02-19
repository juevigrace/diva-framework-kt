package io.github.juevigrace.diva.database.test

import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.isSuccess
import io.github.juevigrace.diva.database.DB
import io.github.juevigrace.diva.database.DivaDB
import io.github.juevigrace.diva.database.DivaDatabase
import io.github.juevigrace.diva.database.driver.DriverProvider
import io.github.juevigrace.diva.database.driver.Schema
import io.github.juevigrace.diva.database.driver.configuration.DriverConf
import io.github.juevigrace.diva.database.driver.configuration.JvmConf
import io.github.juevigrace.diva.database.driver.factory.JvmDriverProviderFactory
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class DivaDBTest {
    companion object {
        private val provider: DriverProvider = JvmDriverProviderFactory(
            JvmConf(
                DriverConf.PostgresqlDriverConf(
                    host = "localhost",
                    port = 5434,
                    username = "postgres",
                    password = "postgres",
                    database = "pg",
                    schema = "public",
                )
            )
        ).create()
        private val db: DivaDatabase<DB> by lazy {
            val result = provider.createDriver(Schema.Sync(DB.Schema))
            assert(result.isSuccess()) {
                "INITIALIZATION ERROR: ${(result as DivaResult.Failure).err}"
            }

            val success: SqlDriver = (result as DivaResult.Success<SqlDriver>).value
            DivaDatabase(success, DB(success))
        }

        val divaDB = DivaDB(db)
    }

    @Test
    fun `test check health`() = runTest {
        val check = db.checkHealth()
        assert(check.isSuccess()) {
            "CHECK HEALTH ERROR: ${(check as DivaResult.Failure).err}"
        }
    }

    @Test
    fun `test count users query`() = runTest {
        val count = divaDB.count()
        assert(count.isSuccess()) {
            "COUNT ERROR: ${(count as DivaResult.Failure).err}"
        }
        println((count as DivaResult.Success<Long>).value)
    }

    @Test
    fun `test close`() = runTest {
        val close = db.close()
        assert(close.isSuccess())
    }
}
