package io.github.juevigrace.diva.database

import co.touchlab.sqliter.interop.SQLiteException
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.ConstraintViolationException
import io.github.juevigrace.diva.core.errors.DatabaseLockedException
import io.github.juevigrace.diva.core.errors.DivaDatabaseException
import io.github.juevigrace.diva.core.errors.DuplicateKeyException
import io.github.juevigrace.diva.core.errors.ForeignKeyViolationException

actual fun Throwable.toDivaDatabaseException(): DivaDatabaseException {
    return when (this) {
        is SQLiteException -> {
            val msg = message ?: ""
            when {
                msg.contains("BUSY", ignoreCase = true) ||
                    msg.contains("LOCKED", ignoreCase = true) ->
                    DatabaseLockedException(details = Option.of(msg))
                msg.contains("FOREIGN KEY", ignoreCase = true) ||
                    msg.contains("foreign key", ignoreCase = true) ->
                    ForeignKeyViolationException(details = Option.of(msg))
                msg.contains("UNIQUE", ignoreCase = true) ||
                    msg.contains("duplicate", ignoreCase = true) ->
                    DuplicateKeyException(details = Option.of(msg))
                msg.contains("NOT NULL", ignoreCase = true) ||
                    msg.contains("constraint", ignoreCase = true) ->
                    ConstraintViolationException(
                        table = Option.None,
                        constraint = "constraint",
                        details = Option.of(msg)
                    )
                else -> DivaDatabaseException(
                    message = "Native SQLite error: $msg",
                    cause = this
                )
            }
        }
        else -> DivaDatabaseException(message = message ?: "Unknown error", cause = this)
    }
}
