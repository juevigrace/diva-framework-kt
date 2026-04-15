package io.github.juevigrace.diva.database

import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.ConstraintViolationException
import io.github.juevigrace.diva.core.errors.DatabaseLockedException
import io.github.juevigrace.diva.core.errors.DivaDatabaseException
import io.github.juevigrace.diva.core.errors.DuplicateKeyException
import io.github.juevigrace.diva.core.errors.ForeignKeyViolationException
import io.github.juevigrace.diva.core.util.logError

actual fun Throwable.toDivaDatabaseException(): DivaDatabaseException {
    val result = when (this) {
        is DivaDatabaseException -> this
        is android.database.sqlite.SQLiteConstraintException -> {
            when {
                message?.contains("UNIQUE", ignoreCase = true) == true ->
                    DuplicateKeyException(details = Option.of(message), cause = this)
                message?.contains("foreign key", ignoreCase = true) == true ->
                    ForeignKeyViolationException(details = Option.of(message), cause = this)
                else -> ConstraintViolationException(
                    table = Option.None,
                    constraint = "constraint",
                    details = Option.of(message),
                    cause = this
                )
            }
        }
        is android.database.sqlite.SQLiteDatabaseCorruptException ->
            DivaDatabaseException(message = "Database corrupted: $message", cause = this)
        is android.database.sqlite.SQLiteDiskIOException ->
            DatabaseLockedException(details = Option.of(message), cause = this)
        is android.database.sqlite.SQLiteReadOnlyDatabaseException ->
            DivaDatabaseException(message = "Database read-only: $message", cause = this)
        is android.database.sqlite.SQLiteException ->
            DivaDatabaseException(message = "Android SQLite error: $message", cause = this)
        else -> DivaDatabaseException(message = message ?: "Unknown error", cause = this)
    }
    logError(result::class.simpleName ?: "DivaDatabaseException", result.message ?: result.toString())
    return result
}
