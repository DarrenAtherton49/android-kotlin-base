package com.darrenatherton.droidcommunity.common.threading

import rx.Scheduler
import rx.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RxIoExecutor @Inject constructor() : IoExecutor {

    override val scheduler: Scheduler
        get() = Schedulers.io()
}