package dev.stukalo.network.util

import dev.stukalo.common.exception.ApiException
import java.net.HttpURLConnection

interface ExceptionHandler {
    fun handleException(ex: Exception): ApiException {
        if (ex is retrofit2.HttpException) {
            when (ex.code()) {
                in 400..499 -> {
                    return when (ex.code()) {
                        HttpURLConnection.HTTP_NOT_FOUND -> ApiException.NotFound()
                        HttpURLConnection.HTTP_CLIENT_TIMEOUT -> ApiException.RequestTimeout()
                        429 -> ApiException.TooManyRequests()
                        else -> ApiException.UnknownClientError()
                    }
                }
                in 500..599 -> {
                    return if (ex.code() == HttpURLConnection.HTTP_UNAVAILABLE) {
                        ApiException.ServiceUnavailable()
                    } else {
                        ApiException.UnknownServerError()
                    }
                }
            }
        }
        return ApiException.UnknownException()
    }
}