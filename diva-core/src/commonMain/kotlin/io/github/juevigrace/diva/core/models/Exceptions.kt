package io.github.juevigrace.diva.core.models

fun Exception.toDivaError(origin: String? = null): DivaError {
    return if (this is DivaErrorException) {
        this.divaError
    } else {
        DivaError.exception(
            e = this,
            origin = origin,
        )
    }
}
