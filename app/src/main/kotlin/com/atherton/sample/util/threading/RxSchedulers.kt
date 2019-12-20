package com.atherton.sample.util.threading

import io.reactivex.Scheduler
import javax.inject.Singleton

@Singleton
data class RxSchedulers(val io: Scheduler, val main: Scheduler)
