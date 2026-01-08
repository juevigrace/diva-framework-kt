package io.github.juevigrace.diva.core.errors

class DivaErrorException(val divaError: DivaError) : Exception(divaError.message, divaError.cause)
