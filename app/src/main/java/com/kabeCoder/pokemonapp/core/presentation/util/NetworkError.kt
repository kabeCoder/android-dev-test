package com.kabeCoder.pokemonapp.core.presentation.util

import android.content.Context
import com.kabeCoder.pokemonapp.R
import com.kabeCoder.pokemonapp.core.domain.util.DataError

fun DataError.Network.toString(context: Context): String {
    val resId = when(this){
        DataError.Network.UNAUTHORIZED -> R.string.unauthorized_error
        DataError.Network.BAD_REQUEST -> R.string.bad_request_error
        DataError.Network.NOT_FOUND -> R.string.not_found_error
        DataError.Network.NOT_ACCEPTABLE -> R.string.not_acceptable_error
        DataError.Network.TOO_MANY_REQUEST -> R.string.too_many_request_error
        DataError.Network.INTERNAL_SERVER_ERROR -> R.string.internal_server_error
        DataError.Network.SERVICE_UNAVAILABLE -> R.string.service_unavailable
        DataError.Network.SERIALIZATION -> R.string.serialization_error
        DataError.Network.NO_INTERNET -> R.string.no_internet
        DataError.Network.UNKNOWN -> R.string.unknown_error
        DataError.Network.REQUEST_TIMEOUT -> TODO()
        DataError.Network.CONFLICT -> TODO()
        DataError.Network.TOO_MANY_REQUESTS -> TODO()
        DataError.Network.PAYLOAD_TOO_LARGE -> TODO()
        DataError.Network.SERVER_ERROR -> TODO()
    }

    return context.getString(resId)
}