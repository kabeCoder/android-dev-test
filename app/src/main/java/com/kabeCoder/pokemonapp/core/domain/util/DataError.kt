package com.kabeCoder.pokemonapp.core.domain.util

sealed interface DataError: Error {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN,
        BAD_REQUEST,
        NOT_FOUND,
        NOT_ACCEPTABLE,
        TOO_MANY_REQUEST,
        INTERNAL_SERVER_ERROR,
        SERVICE_UNAVAILABLE,
    }

    enum class Local: DataError {
        DISK_FULL
    }
}