package com.example.base.domain

import rx.Observable

abstract class ReactiveUseCaseChild<ObservableType> {

    abstract fun observable(): Observable<ObservableType>
}