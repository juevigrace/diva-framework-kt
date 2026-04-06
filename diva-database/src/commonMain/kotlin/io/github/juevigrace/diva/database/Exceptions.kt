package io.github.juevigrace.diva.database

import io.github.juevigrace.diva.core.errors.DivaDatabaseException

expect fun Throwable.toDivaDatabaseException(): DivaDatabaseException
