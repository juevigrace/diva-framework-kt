package io.github.juevigrace.diva.core.network

sealed class HttpStatusCodes(val code: Int, val description: String) {
    data object Continue : HttpStatusCodes(STATUS_CONTINUE, "Continue")

    data object SwitchingProtocols : HttpStatusCodes(STATUS_SWITCHING_PROTOCOLS, "Switching Protocols")

    data object Processing : HttpStatusCodes(STATUS_PROCESSING, "Processing")

    data object EarlyHints : HttpStatusCodes(STATUS_EARLY_HINTS, "Early Hints")

    data object OK : HttpStatusCodes(STATUS_OK, "OK")

    data object Created : HttpStatusCodes(STATUS_CREATED, "Created")

    data object Accepted : HttpStatusCodes(STATUS_ACCEPTED, "Accepted")

    data object NonAuthoritativeInformation : HttpStatusCodes(
        code = STATUS_NON_AUTHORITATIVE_INFORMATION,
        description = "Non-Authoritative Information",
    )

    data object NoContent : HttpStatusCodes(STATUS_NO_CONTENT, "No Content")

    data object ResetContent : HttpStatusCodes(STATUS_RESET_CONTENT, "Reset Content")

    data object PartialContent : HttpStatusCodes(STATUS_PARTIAL_CONTENT, "Partial Content")

    data object MultipleChoices : HttpStatusCodes(STATUS_MULTIPLE_CHOICES, "Multiple Choices")

    data object MovedPermanently : HttpStatusCodes(STATUS_MOVED_PERMANENTLY, "Moved Permanently")

    data object Found : HttpStatusCodes(STATUS_FOUND, "Found")

    data object SeeOther : HttpStatusCodes(STATUS_SEE_OTHER, "See Other")

    data object NotModified : HttpStatusCodes(STATUS_NOT_MODIFIED, "Not Modified")

    data object UseProxy : HttpStatusCodes(STATUS_USE_PROXY, "Use Proxy")

    data object TemporaryRedirect : HttpStatusCodes(STATUS_TEMPORARY_REDIRECT, "Temporary Redirect")

    data object PermanentRedirect : HttpStatusCodes(STATUS_PERMANENT_REDIRECT, "Permanent Redirect")

    data object BadRequest : HttpStatusCodes(STATUS_BAD_REQUEST, "Bad Request")

    data object Unauthorized : HttpStatusCodes(STATUS_UNAUTHORIZED, "Unauthorized")

    data object PaymentRequired : HttpStatusCodes(STATUS_PAYMENT_REQUIRED, "Payment Required")

    data object Forbidden : HttpStatusCodes(STATUS_FORBIDDEN, "Forbidden")

    data object NotFound : HttpStatusCodes(STATUS_NOT_FOUND, "Not Found")

    data object MethodNotAllowed : HttpStatusCodes(STATUS_METHOD_NOT_ALLOWED, "Method Not Allowed")

    data object NotAcceptable : HttpStatusCodes(STATUS_NOT_ACCEPTABLE, "Not Acceptable")

    data object ProxyAuthenticationRequired : HttpStatusCodes(
        code = STATUS_PROXY_AUTHENTICATION_REQUIRED,
        description = "Proxy Authentication Required",
    )

    data object RequestTimeout : HttpStatusCodes(STATUS_REQUEST_TIMEOUT, "Request Timeout")

    data object Conflict : HttpStatusCodes(STATUS_CONFLICT, "Conflict")

    data object Gone : HttpStatusCodes(STATUS_GONE, "Gone")

    data object LengthRequired : HttpStatusCodes(STATUS_LENGTH_REQUIRED, "Length Required")

    data object PreconditionFailed : HttpStatusCodes(STATUS_PRECONDITION_FAILED, "Precondition Failed")

    data object PayloadTooLarge : HttpStatusCodes(STATUS_PAYLOAD_TOO_LARGE, "Payload Too Large")

    data object URITooLong : HttpStatusCodes(STATUS_URI_TOO_LONG, "URI Too Long")

    data object UnsupportedMediaType : HttpStatusCodes(STATUS_UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type")

    data object RangeNotSatisfiable : HttpStatusCodes(STATUS_RANGE_NOT_SATISFIABLE, "Range Not Satisfiable")

    data object ExpectationFailed : HttpStatusCodes(STATUS_EXPECTATION_FAILED, "Expectation Failed")

    data object ImATeapot : HttpStatusCodes(STATUS_IM_A_TEAPOT, "I'm a teapot")

    data object UnprocessableEntity : HttpStatusCodes(STATUS_UNPROCESSABLE_ENTITY, "Unprocessable Entity")

    data object TooEarly : HttpStatusCodes(STATUS_TOO_EARLY, "Too Early")

    data object UpgradeRequired : HttpStatusCodes(STATUS_UPGRADE_REQUIRED, "Upgrade Required")

    data object PreconditionRequired : HttpStatusCodes(STATUS_PRECONDITION_REQUIRED, "Precondition Required")

    data object TooManyRequests : HttpStatusCodes(STATUS_TOO_MANY_REQUESTS, "Too Many Requests")

    data object RequestHeaderFieldsTooLarge : HttpStatusCodes(
        STATUS_REQUEST_HEADER_FIELDS_TOO_LARGE,
        "Request Header Fields Too Large",
    )

    data object UnavailableForLegalReasons : HttpStatusCodes(
        STATUS_UNAVAILABLE_FOR_LEGAL_REASONS,
        "Unavailable For Legal Reasons",
    )

    data object InternalServerError : HttpStatusCodes(STATUS_INTERNAL_SERVER_ERROR, "Internal Server Error")

    data object NotImplemented : HttpStatusCodes(STATUS_NOT_IMPLEMENTED, "Not Implemented")

    data object BadGateway : HttpStatusCodes(STATUS_BAD_GATEWAY, "Bad Gateway")

    data object ServiceUnavailable : HttpStatusCodes(STATUS_SERVICE_UNAVAILABLE, "Service Unavailable")

    data object GatewayTimeout : HttpStatusCodes(STATUS_GATEWAY_TIMEOUT, "Gateway Timeout")

    data object HTTPVersionNotSupported : HttpStatusCodes(
        STATUS_HTTP_VERSION_NOT_SUPPORTED,
        "HTTP Version Not Supported",
    )

    data object VariantAlsoNegotiates : HttpStatusCodes(STATUS_VARIANT_ALSO_NEGOTIATES, "Variant Also Negotiates")

    data object InsufficientStorage : HttpStatusCodes(STATUS_INSUFFICIENT_STORAGE, "Insufficient Storage")

    data object LoopDetected : HttpStatusCodes(STATUS_LOOP_DETECTED, "Loop Detected")

    data object NotExtended : HttpStatusCodes(STATUS_NOT_EXTENDED, "Not Extended")

    data object NetworkAuthenticationRequired : HttpStatusCodes(
        STATUS_NETWORK_AUTHENTICATION_REQUIRED,
        "Network Authentication Required",
    )

    data class UnknownStatus(val unknownCode: Int) : HttpStatusCodes(unknownCode, "Unknown Status")

    companion object {
        fun fromInt(code: Int): HttpStatusCodes {
            return when (code) {
                STATUS_CONTINUE -> Continue
                STATUS_SWITCHING_PROTOCOLS -> SwitchingProtocols
                STATUS_PROCESSING -> Processing
                STATUS_EARLY_HINTS -> EarlyHints
                STATUS_OK -> OK
                STATUS_CREATED -> Created
                STATUS_ACCEPTED -> Accepted
                STATUS_NON_AUTHORITATIVE_INFORMATION -> NonAuthoritativeInformation
                STATUS_NO_CONTENT -> NoContent
                STATUS_RESET_CONTENT -> ResetContent
                STATUS_PARTIAL_CONTENT -> PartialContent
                STATUS_MULTIPLE_CHOICES -> MultipleChoices
                STATUS_MOVED_PERMANENTLY -> MovedPermanently
                STATUS_FOUND -> Found
                STATUS_SEE_OTHER -> SeeOther
                STATUS_NOT_MODIFIED -> NotModified
                STATUS_USE_PROXY -> UseProxy
                STATUS_TEMPORARY_REDIRECT -> TemporaryRedirect
                STATUS_PERMANENT_REDIRECT -> PermanentRedirect
                STATUS_BAD_REQUEST -> BadRequest
                STATUS_UNAUTHORIZED -> Unauthorized
                STATUS_PAYMENT_REQUIRED -> PaymentRequired
                STATUS_FORBIDDEN -> Forbidden
                STATUS_NOT_FOUND -> NotFound
                STATUS_METHOD_NOT_ALLOWED -> MethodNotAllowed
                STATUS_NOT_ACCEPTABLE -> NotAcceptable
                STATUS_PROXY_AUTHENTICATION_REQUIRED -> ProxyAuthenticationRequired
                STATUS_REQUEST_TIMEOUT -> RequestTimeout
                STATUS_CONFLICT -> Conflict
                STATUS_GONE -> Gone
                STATUS_LENGTH_REQUIRED -> LengthRequired
                STATUS_PRECONDITION_FAILED -> PreconditionFailed
                STATUS_PAYLOAD_TOO_LARGE -> PayloadTooLarge
                STATUS_URI_TOO_LONG -> URITooLong
                STATUS_UNSUPPORTED_MEDIA_TYPE -> UnsupportedMediaType
                STATUS_RANGE_NOT_SATISFIABLE -> RangeNotSatisfiable
                STATUS_EXPECTATION_FAILED -> ExpectationFailed
                STATUS_IM_A_TEAPOT -> ImATeapot
                STATUS_UNPROCESSABLE_ENTITY -> UnprocessableEntity
                STATUS_TOO_EARLY -> TooEarly
                STATUS_UPGRADE_REQUIRED -> UpgradeRequired
                STATUS_PRECONDITION_REQUIRED -> PreconditionRequired
                STATUS_TOO_MANY_REQUESTS -> TooManyRequests
                STATUS_REQUEST_HEADER_FIELDS_TOO_LARGE -> RequestHeaderFieldsTooLarge
                STATUS_UNAVAILABLE_FOR_LEGAL_REASONS -> UnavailableForLegalReasons
                STATUS_INTERNAL_SERVER_ERROR -> InternalServerError
                STATUS_NOT_IMPLEMENTED -> NotImplemented
                STATUS_BAD_GATEWAY -> BadGateway
                STATUS_SERVICE_UNAVAILABLE -> ServiceUnavailable
                STATUS_GATEWAY_TIMEOUT -> GatewayTimeout
                STATUS_HTTP_VERSION_NOT_SUPPORTED -> HTTPVersionNotSupported
                STATUS_VARIANT_ALSO_NEGOTIATES -> VariantAlsoNegotiates
                STATUS_INSUFFICIENT_STORAGE -> InsufficientStorage
                STATUS_LOOP_DETECTED -> LoopDetected
                STATUS_NOT_EXTENDED -> NotExtended
                STATUS_NETWORK_AUTHENTICATION_REQUIRED -> NetworkAuthenticationRequired
                else -> UnknownStatus(code)
            }
        }

        // Individual status code constants
        const val STATUS_CONTINUE = 100
        const val STATUS_SWITCHING_PROTOCOLS = 101
        const val STATUS_PROCESSING = 102
        const val STATUS_EARLY_HINTS = 103

        const val STATUS_OK = 200
        const val STATUS_CREATED = 201
        const val STATUS_ACCEPTED = 202
        const val STATUS_NON_AUTHORITATIVE_INFORMATION = 203
        const val STATUS_NO_CONTENT = 204
        const val STATUS_RESET_CONTENT = 205
        const val STATUS_PARTIAL_CONTENT = 206

        const val STATUS_MULTIPLE_CHOICES = 300
        const val STATUS_MOVED_PERMANENTLY = 301
        const val STATUS_FOUND = 302
        const val STATUS_SEE_OTHER = 303
        const val STATUS_NOT_MODIFIED = 304
        const val STATUS_USE_PROXY = 305
        const val STATUS_TEMPORARY_REDIRECT = 307
        const val STATUS_PERMANENT_REDIRECT = 308

        const val STATUS_BAD_REQUEST = 400
        const val STATUS_UNAUTHORIZED = 401
        const val STATUS_PAYMENT_REQUIRED = 402
        const val STATUS_FORBIDDEN = 403
        const val STATUS_NOT_FOUND = 404
        const val STATUS_METHOD_NOT_ALLOWED = 405
        const val STATUS_NOT_ACCEPTABLE = 406
        const val STATUS_PROXY_AUTHENTICATION_REQUIRED = 407
        const val STATUS_REQUEST_TIMEOUT = 408
        const val STATUS_CONFLICT = 409
        const val STATUS_GONE = 410
        const val STATUS_LENGTH_REQUIRED = 411
        const val STATUS_PRECONDITION_FAILED = 412
        const val STATUS_PAYLOAD_TOO_LARGE = 413
        const val STATUS_URI_TOO_LONG = 414
        const val STATUS_UNSUPPORTED_MEDIA_TYPE = 415
        const val STATUS_RANGE_NOT_SATISFIABLE = 416
        const val STATUS_EXPECTATION_FAILED = 417
        const val STATUS_IM_A_TEAPOT = 418
        const val STATUS_UNPROCESSABLE_ENTITY = 422
        const val STATUS_TOO_EARLY = 425
        const val STATUS_UPGRADE_REQUIRED = 426
        const val STATUS_PRECONDITION_REQUIRED = 428
        const val STATUS_TOO_MANY_REQUESTS = 429
        const val STATUS_REQUEST_HEADER_FIELDS_TOO_LARGE = 431
        const val STATUS_UNAVAILABLE_FOR_LEGAL_REASONS = 451

        const val STATUS_INTERNAL_SERVER_ERROR = 500
        const val STATUS_NOT_IMPLEMENTED = 501
        const val STATUS_BAD_GATEWAY = 502
        const val STATUS_SERVICE_UNAVAILABLE = 503
        const val STATUS_GATEWAY_TIMEOUT = 504
        const val STATUS_HTTP_VERSION_NOT_SUPPORTED = 505
        const val STATUS_VARIANT_ALSO_NEGOTIATES = 506
        const val STATUS_INSUFFICIENT_STORAGE = 507
        const val STATUS_LOOP_DETECTED = 508
        const val STATUS_NOT_EXTENDED = 510
        const val STATUS_NETWORK_AUTHENTICATION_REQUIRED = 511
    }
}
