package com.darrenatherton.droidcommunity.common.threading

import rx.Scheduler

interface UiThread {
    val scheduler: Scheduler
}