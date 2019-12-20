package com.atherton.sample.data.network.model

import com.squareup.moshi.Json

/*
 * Network/data models. The below models should always be mapped to domain or Room models before use.
 */

// Retrofit/Moshi deserialize to this class when there is an API error.
data class SampleApiError(
    @Json(name = "status_message") val statusMessage: String,
    @Json(name = "status_code") val statusCode: Int
)
