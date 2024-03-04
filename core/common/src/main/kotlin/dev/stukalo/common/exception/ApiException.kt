package dev.stukalo.common.exception

import dev.stukalo.common.R

sealed class ApiException(
    open val errorMessage: Int = 0,
) : Exception() {
    data class UnknownClientError(
        override val errorMessage: Int = R.string.unknown_client_error,
    ) : ApiException(errorMessage)

    data class NotFound(
        override val errorMessage: Int = R.string.error_404,
    ) : ApiException(errorMessage)

    data class RequestTimeout(
        override val errorMessage: Int = R.string.error_408,
    ) : ApiException(errorMessage)

    data class TooManyRequests(
        override val errorMessage: Int = R.string.error_429,
    ) : ApiException(errorMessage)

    data class UnknownServerError(
        override val errorMessage: Int = R.string.unknown_server_error,
    ) : ApiException(errorMessage)

    data class ServiceUnavailable(
        override val errorMessage: Int = R.string.error_503,
    ) : ApiException(errorMessage)

    data class UnknownException(
        override val errorMessage: Int = R.string.unknown_error,
    ) : ApiException(errorMessage)
}
