package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import io.github.juevigrace.diva.types.DivaError
import io.github.juevigrace.diva.types.DivaResult
import org.w3c.dom.Worker

actual class DriverProviderImpl : DriverProvider {
    private val worker =
        Worker(
            js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)"""),
        )

    actual override suspend fun createDriver(schema: Schema): DivaResult<SqlDriver, DivaError> {
        return try {
            val driver: WebWorkerDriver =
                WebWorkerDriver(worker).also { d ->
                    when (schema) {
                        is Schema.Async -> schema.value.awaitCreate(d)
                        is Schema.Sync -> schema.value.awaitCreate(d)
                    }
                }
            DivaResult.success(driver)
        } catch (e: Exception) {
            DivaResult.failure(DivaError.database("CREATE", null, e.message, e))
        }
    }
}
