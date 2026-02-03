package io.github.juevigrace.diva.core.errors

import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.ifPresent
import io.github.juevigrace.diva.core.network.HttpRequestMethod
import io.github.juevigrace.diva.core.network.HttpStatusCodes

sealed interface ErrorCause {
    override fun toString(): String

    data class Ex(
        val ex: Exception,
        val details: Option<String> = Option.None,
    ) : ErrorCause {
        override fun toString(): String {
            return buildString {
                append("exception")
                ex.message?.let { append(": $it") }
                ex.cause?.let { append(" - cause: $it") }
                details.ifPresent { append(" - ($it)") }
            }
        }
    }

    sealed class Database(
        open val action: DatabaseAction,
        open val table: Option<String> = Option.None,
        open val details: Option<String> = Option.None,
    ) : ErrorCause {
        data class NoRowsAffected(
            override val action: DatabaseAction,
            override val table: Option<String> = Option.None,
            override val details: Option<String> = Option.None,
        ) : Database(
            action = action,
            table = table,
            details = details
        )

        data class Duplicated(
            val field: String,
            val value: String,
            override val action: DatabaseAction,
            override val table: Option<String> = Option.None,
            override val details: Option<String> = Option.Some("$field: $value"),
        ) : Database(
            action = action,
            table = table,
            details = details
        )

        override fun toString(): String {
            return buildString {
                append("database error: $action")
                table.ifPresent { append(" - table $it") }
                details.ifPresent { append(" - ($it)") }
            }
        }
    }

    data class Network(
        val method: HttpRequestMethod,
        val url: String,
        val status: HttpStatusCodes,
        val details: Option<String> = Option.None,
    ) : ErrorCause {
        override fun toString(): String {
            return buildString {
                append("network error: $method ${status.code} $url")
                details.ifPresent { append(" - ($it)") }
            }
        }
    }

    sealed class Validation(
        val message: String
    ) : ErrorCause {
        data class UnexpectedValue(
            val field: String,
            val expectedValue: String,
            val value: String,
            val details: Option<String> = Option.None,
        ) : Validation(
            message = buildString {
                append("expected $expectedValue for $field, got $value")
                details.ifPresent { append(" - ($it)") }
            }
        )

        data class MissingValue(
            val field: String,
            val details: Option<String> = Option.None
        ) : Validation(
            message = buildString {
                append("missing value for $field")
                details.ifPresent { append(" - ($it)") }
            }
        )

        data class Parse(
            val field: String,
            val details: Option<String> = Option.None
        ) : Validation(
            message = buildString {
                append("failed to parse $field")
                details.ifPresent { append(" - ($it)") }
            }
        )

        override fun toString(): String {
            return "validation error: $message"
        }
    }
}

data class DivaError(
    val cause: ErrorCause,
) {
    val message: String = cause.toString()
}
