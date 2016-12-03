package com.example.common.threading

import rx.Scheduler

interface UiExecutor {
    val scheduler: Scheduler
}