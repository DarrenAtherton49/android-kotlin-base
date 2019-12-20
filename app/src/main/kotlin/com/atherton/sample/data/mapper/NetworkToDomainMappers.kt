package com.atherton.sample.data.mapper

import com.atherton.sample.data.network.model.NetworkResponse
import com.atherton.sample.data.network.model.SampleApiError
import com.atherton.sample.domain.model.ApiError
import com.atherton.sample.domain.model.LceResponse

/*
 * A collection of extension functions to map from network models to app-level/domain models.
 *
 * NOTE - When mapping from a network model to a domain model, we filter out any objects which have a null id.
 */

internal fun <NETWORK : Any, DOMAIN : Any> NetworkResponse<NETWORK, SampleApiError>.toDomainLceResponse(
    data: DOMAIN?,
    fallbackData: DOMAIN? = data
): LceResponse<DOMAIN> {
    return when {
        this is NetworkResponse.Success && data != null -> LceResponse.Content(data)
        this is NetworkResponse.ServerError<SampleApiError> -> {
            LceResponse.Error.ServerError(error?.toDomainApiError(), code, fallbackData)
        }
        this is NetworkResponse.NetworkError -> LceResponse.Error.NetworkError(error, fallbackData)
        else -> throw IllegalStateException("Data should not be null if network response was a success")
    }
}

private fun SampleApiError.toDomainApiError(): ApiError = ApiError(statusMessage, statusCode)
