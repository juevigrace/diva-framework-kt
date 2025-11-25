package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import io.github.juevigrace.diva.types.DivaResult
import io.github.juevigrace.diva.types.isFailure
import org.w3c.dom.Worker

actual class DriverProviderImpl(
    private val conf: PlatformDriverConf.Web,
) : DriverProvider {
    private val worker = Worker(
        js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)"""),
    )

    actual override suspend fun createDriver(schema: Schema): DivaResult<SqlDriver, Exception> {
        return try {
            val driver: WebWorkerDriver = WebWorkerDriver(worker).also { d ->
                when (schema) {
                    is Schema.Async -> schema.value.awaitCreate(d)
                    is Schema.Sync -> schema.value.awaitCreate(d)
                }
            }
            DivaResult.success(driver)
        } catch (e: Exception) {
            DivaResult.failure(e)
        }
    }

    actual class Builder : DriverProvider.Builder {
        private var conf: DivaResult<PlatformDriverConf.Web, Exception> = DivaResult.failure(
            Exception("Platform configuration is not set"),
        )

        actual override fun setPlatformConf(platformConf: PlatformDriverConf): Builder {
            return apply {
                this.conf = when (platformConf) {
                    is PlatformDriverConf.Web -> {
                        DivaResult.success(platformConf)
                    }
                    else -> {
                        DivaResult.failure(Exception("PlatformDriverConfig must be Web"))
                    }
                }
            }
        }

        actual override fun build(): DivaResult<DriverProvider, Exception> {
            return try {
                if (conf.isFailure()) throw (conf as DivaResult.Failure).error
                DivaResult.success(DriverProviderImpl((conf as DivaResult.Success).value))
            } catch (e: IllegalStateException) {
                DivaResult.failure(e)
            }
        }
    }
}
