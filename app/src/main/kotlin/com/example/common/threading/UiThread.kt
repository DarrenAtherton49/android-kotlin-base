package com.example.common.threading

import rx.Scheduler

interface UiThread {
    val scheduler: Scheduler
}