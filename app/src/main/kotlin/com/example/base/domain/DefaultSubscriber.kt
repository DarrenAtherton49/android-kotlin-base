package com.example.base.domain

class DefaultSubscriber<T> : rx.Subscriber<T>() {
    override fun onCompleted() {
    }

    override fun onError(e: Throwable) {
    }

    override fun onNext(t: T) {
    }
}