package io.github.juevigrace.diva.core.errors

import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseOperation

open class DivaDatabaseException(
    message: String,
    override val cause: Throwable? = null
) : Exception(message, cause) {
    open val operation: Option<DatabaseOperation> = Option.None
    open val table: Option<String> = Option.None
    open val details: Option<String> = Option.None
}

// No rows affected
class NoRowsAffectedException(
    override val operation: Option<DatabaseOperation>,
    override val table: Option<String> = Option.None,
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaDatabaseException("No rows affected", cause)

// Constraint violation
class ConstraintViolationException(
    override val table: Option<String> = Option.None,
    constraint: String,
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaDatabaseException("Constraint violation: $constraint", cause)

// Connection error
class DatabaseConnectionException(
    override val details: Option<String>,
    cause: Throwable? = null
) : DivaDatabaseException("Connection failed", cause) {
    override val operation: Option<DatabaseOperation> = Option.of(DatabaseOperation.CONNECT)
}

// Timeout
class DatabaseTimeoutException(
    override val operation: Option<DatabaseOperation>,
    override val table: Option<String> = Option.None,
    override val details: Option<String> = Option.None,
    cause: Throwable? = null
) : DivaDatabaseException("Query timeout", cause)