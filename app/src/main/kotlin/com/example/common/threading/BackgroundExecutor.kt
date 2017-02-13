package com.darrenatherton.droidcommunity.common.threading

import rx.Scheduler

interface BackgroundExecutor {
    val scheduler: Scheduler
}