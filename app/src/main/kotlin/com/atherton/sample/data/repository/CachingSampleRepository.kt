package com.atherton.sample.data.repository

import com.atherton.sample.data.db.dao.SampleDao
import com.atherton.sample.data.network.service.SampleService
import com.atherton.sample.domain.repository.SampleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CachingSampleRepository @Inject constructor(
    private val sampleDao: SampleDao,
    private val  sampleService: SampleService
) : SampleRepository {


}
