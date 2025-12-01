package io.github.juevigrace.diva.database.driver

import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.types.DivaError
import io.github.juevigrace.diva.types.DivaResult

actual class DriverProviderImpl : DriverProvider {
    actual override suspend fun createDriver(schema: Schema): DivaResult<SqlDriver, DivaError> {
        return try {
            // Handle both sync and async schemas for WASM JS
            when (schema) {
                is Schema.Async -> {
                    // TODO: Implement async schema handling for WASM JS
                    TODO("Async schema support not yet implemented for WASM JS")
                }
                is Schema.Sync -> {
                    // TODO: Implement sync schema handling for WASM JS
                    TODO("Sync schema support not yet implemented for WASM JS")
                }
            }
        } catch (e: Exception) {
            DivaResult.failure(DivaError.database("CREATE", null, e.message, e))
        }
    }
}
