package com.example.base.domain

import com.example.common.threading.BackgroundExecutor
import com.example.common.threading.UiThread
import rx.Observable
import rx.Subscription

abstract class ReactiveUseCase<ObservableType> (
        private val uiThread: UiThread,
        private val backgroundExecutor: BackgroundExecutor) {

    protected fun executeUseCase(onNext: (ObservableType) -> Unit = {},
                                 onError: (Throwable) -> Unit = {},
                                 onCompleted: () -> Unit = {}): Subscription {
        return useCaseObservable()
                .subscribeOn(backgroundExecutor.scheduler)
                .observeOn(uiThread.scheduler)
                .subscribe(onNext, onError, onCompleted)
    }

    protected abstract fun useCaseObservable(): Observable<ObservableType>
}