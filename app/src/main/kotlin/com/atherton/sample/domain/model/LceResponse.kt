package com.atherton.sample.domain.model

import java.io.IOException

/**
 * Represents the result of making a repository request which can emit data while loading.
 *
 * The 'Loading' state is used when we want to emit the fact that we are loading data, but in the meantime
 * we have some data to show already (e.g. from database).
 *
 * The 'Content' state is used to emit our intended/fresh data.
 *
 * The 'Error' state is used to emit an error in fetching data, but can also contain some fallback data (e.g.
 * from database).
 *
 */
sealed class LceResponse<out T : Any> {

    // can assume that any data used in the Loading state is cached
    data class Loading<T : Any>(val data: T?) : LceResponse<T>()

    data class Content<T : Any>(val data: T) : LceResponse<T>()

    // can assume that any data used in the Error state is cached
    sealed class Error<T : Any>(open val fallbackData: T?) : LceResponse<T>() {

        // A non-2XX response that may have an ApiError as its error data.
        data class ServerError<T : Any>(
            val error: ApiError?,
            val code: Int,
            override val fallbackData: T?
        ) : Error<T>(fallbackData)

        // A request that didn't result in a response from the server.
        data class NetworkError<T : Any>(
            val error: IOException,
            override val fallbackData: T?
        ) : Error<T>(fallbackData)
    }
}
