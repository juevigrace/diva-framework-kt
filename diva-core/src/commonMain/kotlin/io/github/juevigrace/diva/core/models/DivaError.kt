package io.github.juevigrace.diva.core.models

sealed class DivaError(
    val message: String,
    open val cause: Throwable? = null,
) {
    // Database errors with specific context
    data class DatabaseError(
        val operation: String, // e.g., "INSERT", "SELECT", "UPDATE", "DELETE"
        val table: String? = null, // e.g., "users", "orders"
        val details: String? = null, // e.g., "constraint violation", "connection timeout"
        override val cause: Throwable? = null,
    ) : DivaError(
        message = buildString {
            append("Database error during $operation")
            table?.let { append(" on table '$it'") }
            details?.let { append(": $it") }
        },
        cause = cause,
    )

    // Network errors with specific context
    data class NetworkError(
        val operation: String, // e.g., "GET", "POST", "PUT", "DELETE"
        val url: String? = null, // e.g., "https://api.example.com/users"
        val statusCode: Int? = null, // e.g., 404, 500, 401
        val details: String? = null, // e.g., "timeout", "connection refused"
        override val cause: Throwable? = null,
    ) : DivaError(
        message = buildString {
            append("Network error during $operation")
            url?.let { append(" to '$it'") }
            statusCode?.let { append(" (status $it)") }
            details?.let { append(": $it") }
        },
        cause = cause,
    )

    data class ValidationError(
        val field: String? = null, // e.g., "email", "password"
        val constraint: String? = null, // e.g., "required", "invalid format"
        val details: String? = null, // e.g., "must be at least 8 characters"
    ) : DivaError(
        message = buildString {
            append("Validation error")
            field?.let { append(" for field '$it'") }
            constraint?.let { append(": $it") }
            details?.let { append(" - $it") }
        },
    )

    data class ConfigurationError(
        val key: String? = null, // e.g., "database.url", "api.key"
        val details: String? = null, // e.g., "missing required config", "invalid format"
    ) : DivaError(
        message = buildString {
            append("Configuration error")
            key?.let { append(" for '$it'") }
            details?.let { append(": $it") }
        },
    )

    data class UnknownError(
        val details: String? = null,
        override val cause: Throwable? = null,
    ) : DivaError(
        message = buildString {
            append("Unknown error")
            details?.let { append(": $it") }
        },
        cause = cause,
    )

    companion object {
        fun database(
            operation: String,
            table: String? = null,
            details: String? = null,
            cause: Throwable? = null,
        ): DatabaseError {
            return DatabaseError(operation, table, details, cause)
        }

        fun network(
            operation: String,
            url: String? = null,
            statusCode: Int? = null,
            details: String? = null,
            cause: Throwable? = null,
        ): NetworkError {
            return NetworkError(operation, url, statusCode, details, cause)
        }

        fun validation(
            field: String? = null,
            constraint: String? = null,
            details: String? = null,
        ): ValidationError {
            return ValidationError(field, constraint, details)
        }

        fun configuration(
            key: String? = null,
            details: String? = null,
        ): ConfigurationError {
            return ConfigurationError(key, details)
        }

        fun unknown(
            details: String? = null,
            cause: Throwable? = null,
        ): UnknownError {
            return UnknownError(details, cause)
        }
    }
}
