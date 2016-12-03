package com.example.common.threading

import rx.Scheduler

interface BackgroundExecutor {
    val scheduler: Scheduler
}