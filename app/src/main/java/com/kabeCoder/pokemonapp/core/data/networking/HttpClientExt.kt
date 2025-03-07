package com.kabeCoder.pokemonapp.core.data.networking

import com.kabeCoder.pokemonapp.BuildConfig
import com.kabeCoder.pokemonapp.core.domain.util.DataError
import com.kabeCoder.pokemonapp.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified Response: Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, Any?> = emptyMap(),
    headers: Map<String, String> = emptyMap()
): Result<Response, DataError.Network> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
            headers.forEach { (key, value) ->
                header(key, value)
            }
        }
    }
}

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError.Network> {
    val response = try {
        execute()
    } catch(e: UnresolvedAddressException) {
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch(e: SerializationException) {
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch(e: Exception) {
        coroutineContext. ensureActive()
        return Result.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError.Network> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch(e: NoTransformationFoundException) {
                Result.Error(DataError.Network.SERIALIZATION)
            }
        }
        400 -> Result.Error(DataError.Network.BAD_REQUEST)
        401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        404 -> Result.Error(DataError.Network.NOT_FOUND)
        406 -> Result.Error(DataError.Network.NOT_ACCEPTABLE)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUEST)
        500 -> Result.Error(DataError.Network.INTERNAL_SERVER_ERROR)
        503 -> Result.Error(DataError.Network.SERVICE_UNAVAILABLE)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}

fun constructRoute(url: String): String {
    return when {
        url.contains(BuildConfig.POKEMON_API_BASE_URL) -> url
        url.startsWith("/") -> BuildConfig.POKEMON_API_BASE_URL + url.drop(1)
        else -> BuildConfig.POKEMON_API_BASE_URL + url
    }
}