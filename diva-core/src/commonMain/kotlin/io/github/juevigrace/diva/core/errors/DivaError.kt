package io.github.juevigrace.diva.core.errors

import io.github.juevigrace.diva.core.database.DatabaseOperation
import io.github.juevigrace.diva.core.network.HttpRequestMethod
import io.github.juevigrace.diva.core.network.HttpStatusCodes

sealed class DivaError(
    open val message: String,
    open val cause: Throwable? = null,
) {
    // Database errors with specific context
    data class DatabaseError(
        val operation: DatabaseOperation, // e.g., "INSERT", "SELECT", "UPDATE", "DELETE"
        val table: String, // e.g., "users", "orders"
        val details: String? = null, // e.g., "constraint violation", "connection timeout"
        override val cause: Throwable? = null,
    ) : DivaError(
        message = buildString {
            append("Database error during ${operation.name}")
            append(" on table '$table'")
            details?.let { append(": $it") }
        },
        cause = cause,
    )

    data class ExceptionError(
        override val message: String,
        override val cause: Throwable? = null,
        val origin: String? = null,
    ) : DivaError(
        message = buildString {
            append("Exception during $origin")
            append(": $message")
        },
        cause = cause,
    )

    // Network errors with specific context
    data class NetworkError(
        val method: HttpRequestMethod, // e.g., "GET", "POST", "PUT", "DELETE"
        val url: String, // e.g., "https://api.example.com/users"
        val statusCode: HttpStatusCodes, // e.g., 404, 500, 401
        val details: String, // e.g., "timeout", "connection refused"
        override val cause: Throwable? = null,
    ) : DivaError(
        message = buildString {
            append("Network error during ${method.name}")
            append(" to '$url'")
            append(" (status ${statusCode.code})")
            append(": $details")
        },
        cause = cause,
    )

    data class ValidationError(
        val field: String, // e.g., "email", "password"
        val constraint: String, // e.g., "required", "invalid format"
        val details: String? = null, // e.g., "must be at least 8 characters"
    ) : DivaError(
        message = buildString {
            append("Validation error")
            append(" for field '$field'")
            append(": $constraint")
            details?.let { append(" - $it") }
        },
    )

    data class ConfigurationError(
        val key: String, // e.g., "database.url", "api.key"
        val details: String? = null, // e.g., "missing required config", "invalid format"
    ) : DivaError(
        message = buildString {
            append("Configuration error")
            append(" for '$key'")
            details?.let { append(": $it") }
        },
    )

    companion object {
        fun exception(e: Exception, origin: String? = null): DivaError {
            return when (e) {
                is DivaErrorException -> e.divaError
                else -> ExceptionError(
                    message = e.message ?: "Unknown exception",
                    cause = e,
                    origin = origin
                )
            }
        }
    }
}
