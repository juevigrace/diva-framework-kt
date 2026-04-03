package io.github.juevigrace.diva.core.database

enum class DatabaseOperation {
    CONNECT, CONFIGURE, CLOSE,
    INSERT, UPDATE, DELETE,
    SELECT, SELECT_ONE, SELECT_LIST,
    TRANSACTION, MIGRATION
}
