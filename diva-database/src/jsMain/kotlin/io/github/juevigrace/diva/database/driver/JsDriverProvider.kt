package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.ConfigureDriverException
import io.github.juevigrace.diva.core.tryResult
import io.github.juevigrace.diva.core.util.logError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Worker

internal class JsDriverProvider : DriverProvider {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    private val worker =
        Worker(
            js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")
        )

    override fun createDriver(schema: Schema): Result<SqlDriver> {
        return tryResult(
            onError = { e ->
                val err = ConfigureDriverException(
                    details = Option.of("Failed to create driver"),
                    cause = e
                )
                logError(err::class.simpleName ?: "ConfigureDriverException", err.message ?: err.toString())
                err
            }
        ) {
            val driver: WebWorkerDriver = WebWorkerDriver(worker).also { d ->
                scope.launch {
                    when (schema) {
                        is Schema.Async -> schema.value.awaitCreate(d)
                        is Schema.Sync -> schema.value.awaitCreate(d)
                    }
                }
            }
            driver
        }
    }
}
