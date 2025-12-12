package io.github.juevigrace.diva.core.models

class DivaErrorException(val divaError: DivaError) : Exception(divaError.message, divaError.cause)
