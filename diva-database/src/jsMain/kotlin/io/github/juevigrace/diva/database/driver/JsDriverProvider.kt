package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.tryResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Worker

internal class JsDriverProvider : DriverProvider {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    private val worker =
        Worker(
            js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)"""),
        )

    override fun createDriver(schema: Schema): DivaResult<SqlDriver, DivaError> {
        return tryResult(
            onError = { e ->
                DivaError.exception(
                    e = e,
                    origin = "JsDriverProvider.createDriver"
                )
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
            DivaResult.success(driver)
        }
    }
}
