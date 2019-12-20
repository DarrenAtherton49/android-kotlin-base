package com.atherton.sample.data.repository

import com.atherton.sample.data.local.AppSettings
import com.atherton.sample.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CachingSettingsRepository @Inject constructor(
    private val settings: AppSettings
) : SettingsRepository {

}
