package io.github.juevigrace.diva.database

import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.ConstraintViolationException
import io.github.juevigrace.diva.core.errors.DatabaseConnectionException
import io.github.juevigrace.diva.core.errors.DatabaseLockedException
import io.github.juevigrace.diva.core.errors.DivaDatabaseException
import io.github.juevigrace.diva.core.errors.DuplicateKeyException
import io.github.juevigrace.diva.core.errors.ForeignKeyViolationException
import io.github.juevigrace.diva.core.errors.SyntaxErrorException
import io.github.juevigrace.diva.core.util.logError
import org.postgresql.util.PSQLState
import org.sqlite.SQLiteErrorCode

actual fun Throwable.toDivaDatabaseException(): DivaDatabaseException {
    val result = when (this) {
        is DivaDatabaseException -> this
        is org.sqlite.SQLiteException -> {
            when (SQLiteErrorCode.getErrorCode(errorCode)) {
                SQLiteErrorCode.SQLITE_BUSY,
                SQLiteErrorCode.SQLITE_LOCKED -> DatabaseLockedException(
                    details = Option.of(message)
                )
                SQLiteErrorCode.SQLITE_CONSTRAINT_FOREIGNKEY -> ForeignKeyViolationException(
                    details = Option.of(message)
                )
                SQLiteErrorCode.SQLITE_CONSTRAINT_UNIQUE -> DuplicateKeyException(
                    details = Option.of(message)
                )
                SQLiteErrorCode.SQLITE_CONSTRAINT -> ConstraintViolationException(
                    table = Option.None,
                    constraint = "constraint",
                    details = Option.of(message)
                )
                else -> DivaDatabaseException(
                    message = "SQLite error: $message",
                    cause = this
                )
            }
        }
        is org.postgresql.util.PSQLException -> {
            val state = sqlState?.let { state ->
                PSQLState.entries.find { it.state == state }
            }
            when (state) {
                PSQLState.UNIQUE_VIOLATION -> DuplicateKeyException(
                    details = Option.of(message)
                )
                PSQLState.FOREIGN_KEY_VIOLATION -> ForeignKeyViolationException(
                    details = Option.of(message)
                )
                PSQLState.NOT_NULL_VIOLATION -> ConstraintViolationException(
                    table = Option.None,
                    constraint = "not null",
                    details = Option.of(message)
                )
                PSQLState.SYNTAX_ERROR -> SyntaxErrorException(
                    details = Option.of(message)
                )
                PSQLState.CONNECTION_UNABLE_TO_CONNECT,
                PSQLState.CONNECTION_REJECTED,
                PSQLState.CONNECTION_FAILURE -> DatabaseConnectionException(
                    details = Option.of(message)
                )
                else -> DivaDatabaseException(
                    message = "PostgreSQL error: $message",
                    cause = this
                )
            }
        }
        is java.sql.SQLIntegrityConstraintViolationException -> {
            when (sqlState) {
                "23505" -> DuplicateKeyException(
                    details = Option.of(message)
                )
                "23503" -> ForeignKeyViolationException(
                    details = Option.of(message)
                )
                "23502" -> ConstraintViolationException(
                    table = Option.None,
                    constraint = "not null",
                    details = Option.of(message)
                )
                else -> ConstraintViolationException(
                    table = Option.None,
                    constraint = "integrity",
                    details = Option.of(message)
                )
            }
        }
        is java.sql.SQLSyntaxErrorException -> SyntaxErrorException(
            details = Option.of(message)
        )
        else -> DivaDatabaseException(
            message = message ?: "Unknown error",
            cause = this
        )
    }
    logError(result::class.simpleName ?: "DivaDatabaseException", result.message ?: result.toString())
    return result
}
