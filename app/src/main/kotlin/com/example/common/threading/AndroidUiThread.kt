package com.darrenatherton.droidcommunity.common.threading

import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidUiThread @Inject constructor() : UiThread {

    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}