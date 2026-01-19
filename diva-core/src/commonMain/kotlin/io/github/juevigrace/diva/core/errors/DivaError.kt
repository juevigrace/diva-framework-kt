package io.github.juevigrace.diva.core.errors

import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.network.HttpRequestMethod
import io.github.juevigrace.diva.core.network.HttpStatusCodes

// TODO: Add typed constraints to better cast and identify errors
sealed class DivaError(
    open val message: String,
    open val cause: Throwable? = null,
) {
    // Database errors with specific context
    data class DatabaseError(
        val operation: DatabaseAction, // e.g., "INSERT", "SELECT", "UPDATE", "DELETE"
        val table: String? = null, // e.g., "users", "orders"
        val details: String? = null, // e.g., "constraint violation", "connection timeout"
        override val cause: Throwable? = null,
    ) : DivaError(
        message = buildString {
            append("database error: ${operation.name}")
            table?.let { append(" on table '$it'") }
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
            append("error:")
            origin?.let { append(" origin '$it'") }
            append(" $message")
            cause?.let { append(" - $it") }
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
            append("request error for $method $url")
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
            append("validation error:")
            append(" field '$field'")
            append(" ($constraint)")
            details?.let { append(" - $it") }
        },
    )

    data class ConfigurationError(
        val key: String, // e.g., "database.url", "api.key"
        val details: String? = null, // e.g., "missing required config", "invalid format"
    ) : DivaError(
        message = buildString {
            append("configuration error:")
            append(" key '$key'")
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
