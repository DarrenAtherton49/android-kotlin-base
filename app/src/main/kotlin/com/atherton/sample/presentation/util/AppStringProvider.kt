package com.atherton.sample.presentation.util

import com.atherton.sample.domain.model.LceResponse

interface AppStringProvider {

    fun <T : Any> generateErrorMessage(error: LceResponse.Error<T>): String
}
