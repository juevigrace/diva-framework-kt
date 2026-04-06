package io.github.juevigrace.diva.core.errors

import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseOperation
import io.github.juevigrace.diva.core.getOrElse
import io.github.juevigrace.diva.core.ifPresent

open class DivaDatabaseException(
    message: String,
    override val cause: Throwable? = null
) : Exception(message, cause) {
    open val operation: Option<DatabaseOperation> = Option.None
    open val table: Option<String> = Option.None
    open val details: Option<String> = Option.None
}

class UseDriverException(
    override val operation: Option<DatabaseOperation>,
    override val table: Option<String> = Option.None,
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaDatabaseException(
    buildMessage(operation, table, "Driver error", details),
    cause
)

class ConfigureDriverException(
    override val operation: Option<DatabaseOperation> = Option.Some(DatabaseOperation.CONFIGURE),
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaDatabaseException(
    buildMessage(operation, Option.None, "Driver configuration error", details),
    cause
)

class NoRowsAffectedException(
    override val operation: Option<DatabaseOperation>,
    override val table: Option<String> = Option.None,
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaDatabaseException(
    buildMessage(operation, table, "No rows affected", details),
    cause
)

class ConstraintViolationException(
    override val table: Option<String>,
    val constraint: String,
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaDatabaseException(
    "Constraint violation on table '${table.getOrElse { "unknown" }}': $constraint",
    cause
)

class DatabaseConnectionException(
    override val details: Option<String>,
    cause: Throwable? = null
) : DivaDatabaseException(
    buildMessage(Option.of(DatabaseOperation.CONNECT), Option.None, "Connection failed", details),
    cause
) {
    override val operation: Option<DatabaseOperation> = Option.of(DatabaseOperation.CONNECT)
}

class DatabaseTimeoutException(
    override val operation: Option<DatabaseOperation>,
    override val table: Option<String> = Option.None,
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaDatabaseException(
    buildMessage(operation, table, "Query timeout", details),
    cause
)

class ForeignKeyViolationException(
    override val table: Option<String> = Option.None,
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaDatabaseException(
    buildMessage(Option.of(DatabaseOperation.DELETE), table, "Foreign key violation", details),
    cause
)

class DuplicateKeyException(
    override val table: Option<String> = Option.None,
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaDatabaseException(
    buildMessage(Option.of(DatabaseOperation.INSERT), table, "Duplicate key", details),
    cause
)

class SyntaxErrorException(
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaDatabaseException(
    buildMessage(Option.of(DatabaseOperation.SELECT), Option.None, "SQL syntax error", details),
    cause
)

class DatabaseLockedException(
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaDatabaseException(
    buildMessage(Option.of(DatabaseOperation.TRANSACTION), Option.None, "Database locked", details),
    cause
)

private fun buildMessage(
    operation: Option<DatabaseOperation>,
    table: Option<String>,
    prefix: String,
    details: Option<String>
): String {
    return buildString {
        append(prefix)
        operation.ifPresent { append(" (operation: ${it.name.lowercase()})") }
        table.ifPresent { append(" on table '$it'") }
        details.ifPresent { append(" - $it") }
    }
}
