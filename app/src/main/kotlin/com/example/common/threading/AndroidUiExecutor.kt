package com.example.common.threading

import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidUiExecutor @Inject constructor() : UiExecutor {

    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}