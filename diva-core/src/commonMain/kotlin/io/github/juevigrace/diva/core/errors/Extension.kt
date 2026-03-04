package io.github.juevigrace.diva.core.errors

import io.github.juevigrace.diva.core.network.HttpStatusCodes

fun Exception.toDivaError(cause: ErrorCause = ErrorCause.Error.Ex(this)): DivaError {
    return DivaError(cause = cause)
}

fun ErrorCause.toHttpStatusCodes(): HttpStatusCodes {
    return when (this) {
        is ErrorCause.Database.Duplicated -> {
            HttpStatusCodes.Conflict
        }
        is ErrorCause.Error.Ex -> {
            HttpStatusCodes.InternalServerError
        }
        is ErrorCause.Error.NotImplemented -> {
            HttpStatusCodes.NotImplemented
        }
        is ErrorCause.Validation.MissingValue, is ErrorCause.Database.NoRowsAffected -> {
            HttpStatusCodes.NotFound
        }
        is ErrorCause.Validation.UnexpectedValue,
        is ErrorCause.Validation.Parse,
        is ErrorCause.Validation.Expired,
        is ErrorCause.Validation.Used -> {
            HttpStatusCodes.BadRequest
        }
        is ErrorCause.Network -> status
        is ErrorCause.Actions.RequiredAction<*>, is ErrorCause.Actions.UnknownAction<*> -> HttpStatusCodes.BadRequest
    }
}
