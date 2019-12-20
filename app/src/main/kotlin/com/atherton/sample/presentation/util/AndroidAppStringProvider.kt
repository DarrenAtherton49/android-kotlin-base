package com.atherton.sample.presentation.util

import android.content.res.Resources
import com.atherton.sample.R
import com.atherton.sample.domain.model.LceResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidAppStringProvider @Inject constructor(private val resources: Resources) : AppStringProvider {

    override fun <T : Any> generateErrorMessage(error: LceResponse.Error<T>): String {
        return when (error) {
            is LceResponse.Error.ServerError -> {
                val apiError = error.error
                if (apiError != null) {
                    resources.getString(R.string.error_message_server_with_error).format(
                        error.code,
                        apiError.statusCode
                    )
                } else {
                    resources.getString(R.string.error_message_server).format(error.code)
                }
            }
            is LceResponse.Error.NetworkError -> resources.getString(R.string.error_message_no_internet)
        }
    }
}
