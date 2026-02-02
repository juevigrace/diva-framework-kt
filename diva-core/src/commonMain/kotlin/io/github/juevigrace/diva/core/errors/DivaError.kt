package io.github.juevigrace.diva.core.errors

import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.ifPresent
import io.github.juevigrace.diva.core.network.HttpRequestMethod
import io.github.juevigrace.diva.core.network.HttpStatusCodes

sealed class ErrorCause(
    open val message: String
) {
    sealed class Database(
        override val message: String
    ) : ErrorCause(message) {
        data object NoRowsAffected : Database(
            message = buildString {
                append("no rows affected")
            }
        )
        data class Duplicated(
            val field: Option<String> = Option.None,
            val value: String,
        ) : Database(
            message = buildString {
                append("duplicated: $value")
                field.ifPresent { append(" $it") }
            }
        )
    }

    sealed class Validation(
        override val message: String
    ) : ErrorCause(message) {
        data object Required : Validation(message = "required")
        data class RequiredField(
            val field: String
        ) : Validation(message = "required field: $field")

        data class Length(
            val field: Option<String> = Option.None,
            val fieldLength: Option<Int> = Option.None,
            val length: Int,
        ) : Validation(
            message = buildString {
                append("required length ($length)")
                field.ifPresent { append(": $it") }
                fieldLength.ifPresent { append(" $it") }
            }
        )

        data class Email(
            val value: String
        ) : Validation(message = "invalid email: $value")

        data class Format(
            val field: Option<String> = Option.None,
            val value: String
        ) : Validation(
            message = buildString {
                append("invalid format: $value")
                field.ifPresent { append(" $it") }
            }
        )
    }

    data class Ex(
        val ex: Exception,
    ) : ErrorCause(
        message = buildString {
            append("exception: ${ex.message}")
            ex.cause?.let { append(" cause: ${it.message}") }
        }
    )
}

sealed class DivaError(
    open val cause: ErrorCause,
    open val message: String
) {
    data class Error(
        override val cause: ErrorCause,
    ) : DivaError(cause, cause.message)

    data class DatabaseError(
        val action: DatabaseAction,
        val table: Option<String> = Option.None,
        override val cause: ErrorCause,
    ) : DivaError(
        cause = cause,
        message = buildString {
            append("database error: ${action.name}")
            table.ifPresent { append(" $it") }
            append(" - ${cause.message}")
        }
    )

    data class NetworkError(
        val method: HttpRequestMethod,
        val url: String,
        val status: HttpStatusCodes,
        override val cause: ErrorCause,
    ) : DivaError(
        cause = cause,
        message = buildString {
            append("network error: ")
            append("${method.name} ${status.code} $url")
            append(" - ${cause.message}")
        }
    )
}
