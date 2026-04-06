package io.github.juevigrace.diva.core.errors

class ConstraintException(
    val field: String,
    val constraint: String,
    val value: String,
    cause: Throwable? = null
) : Exception("Constraint violation on field '$field': $constraint, got $value", cause)

class NotFoundException(
    val resourceType: String,
    val resourceId: String,
    cause: Throwable? = null
) : Exception("$resourceType with id '$resourceId' not found", cause)

class SerializationException(
    val type: String,
    cause: Throwable? = null
) : Exception("Failed to serialize/deserialize type: $type", cause)

class ConfigurationException(
    val key: String,
    cause: Throwable? = null
) : Exception("Invalid configuration for key: $key", cause)

class ProcessingException(
    val operation: String,
    cause: Throwable? = null
) : Exception("Processing failed for operation: $operation", cause)
