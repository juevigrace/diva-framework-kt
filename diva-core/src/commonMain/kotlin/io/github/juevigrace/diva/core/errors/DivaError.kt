package io.github.juevigrace.diva.core.errors

import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.ifPresent
import io.github.juevigrace.diva.core.network.HttpRequestMethod
import io.github.juevigrace.diva.core.network.HttpStatusCodes
import kotlin.text.buildString

sealed class ErrorCause(
    open val message: String,
) {
    sealed class Error(
        override val message: String,
        open val details: Option<String> = Option.None,
    ) : ErrorCause(message) {
        data class Ex(
            val ex: Exception,
            override val details: Option<String> = Option.None,
        ) : Error(
            buildString {
                append("exception")
                ex.message?.let { append(": $it") }
                ex.cause?.let { append(" - cause: $it") }
                details.ifPresent { append(" - ($it)") }
            }
        )

        data class NotImplemented(
            override val details: Option<String> = Option.None,
        ) : Error(
            buildString {
                append("not implemented")
                details.ifPresent { append(" - ($it)") }
            }
        )
    }

    sealed class Database(
        open val action: DatabaseAction,
        open val table: Option<String> = Option.None,
        open val details: Option<String> = Option.None,
        override val message: String
    ) : ErrorCause(message) {
        data class NoRowsAffected(
            override val action: DatabaseAction,
            override val table: Option<String> = Option.None,
            override val details: Option<String> = Option.None,
        ) : Database(
            action = action,
            table = table,
            details = details,
            message = buildString {
                append("database error: $action")
                table.ifPresent { append(" - table $it") }
                append(" - no rows affected")
                details.ifPresent { append(" - ($it)") }
            }
        )

        data class Duplicated(
            val field: String,
            val value: String,
            override val action: DatabaseAction,
            override val table: Option<String> = Option.None,
            override val details: Option<String> = Option.None,
        ) : Database(
            action = action,
            table = table,
            details = details,
            message = buildString {
                append("database error: $action")
                table.ifPresent { append(" - table $it") }
                append(" - duplicated value for $field: $value")
                details.ifPresent { append(" - ($it)") }
            }
        )
    }

    sealed class Network(
        open val method: HttpRequestMethod,
        open val url: String,
        open val status: HttpStatusCodes,
        open val details: Option<String> = Option.None,
        override val message: String,
    ) : ErrorCause(message) {
        data class Error(
            override val method: HttpRequestMethod,
            override val url: String,
            override val status: HttpStatusCodes,
            override val details: Option<String> = Option.None
        ) : Network(
            method = method,
            url = url,
            status = status,
            details = details,
            message = buildString {
                append("network error: $method ${status.code} $url")
                details.ifPresent { append(" - ($it)") }
            }
        )

        data class NoConnection(
            override val method: HttpRequestMethod,
            override val url: String,
            override val status: HttpStatusCodes,
            override val details: Option<String> = Option.None
        ) : Network(
            method = method,
            url = url,
            status = status,
            details = details,
            message = buildString {
                append("network error: $method ${status.code} $url")
                append(" - no connection")
                details.ifPresent { append(" - ($it)") }
            }
        )

        data class Timeout(
            override val method: HttpRequestMethod,
            override val url: String,
            override val status: HttpStatusCodes,
            override val details: Option<String> = Option.None
        ) : Network(
            method = method,
            url = url,
            status = status,
            details = details,
            message = buildString {
                append("network error: $method ${status.code} $url")
                append(" - timeout")
                details.ifPresent { append(" - ($it)") }
            }
        )
    }

    sealed class Validation(
        open val field: String,
        override val message: String
    ) : ErrorCause(message) {
        data class UnexpectedValue(
            override val field: String,
            val expectedValue: String,
            val value: String,
            val details: Option<String> = Option.None,
        ) : Validation(
            field = field,
            message = buildString {
                append("expected $expectedValue for $field, got $value")
                details.ifPresent { append(" - ($it)") }
            }
        )

        data class Used(
            override val field: String,
            val details: Option<String> = Option.None
        ) : Validation(
            field = field,
            message = buildString {
                append("$field already used")
                details.ifPresent { append(" - ($it)") }
            }
        )

        data class Expired(
            override val field: String,
            val details: Option<String> = Option.None
        ) : Validation(
            field = field,
            message = buildString {
                append("$field expired")
                details.ifPresent { append(" - ($it)") }
            }
        )

        data class MissingValue(
            override val field: String,
            val details: Option<String> = Option.None
        ) : Validation(
            field = field,
            message = buildString {
                append("missing value for $field")
                details.ifPresent { append(" - ($it)") }
            }
        )

        data class Parse(
            override val field: String,
            val details: Option<String> = Option.None
        ) : Validation(
            field = field,
            message = buildString {
                append("failed to parse $field")
                details.ifPresent { append(" - ($it)") }
            }
        )
    }
}

data class DivaError(
    val cause: ErrorCause,
) {
    val message: String = cause.message
}
