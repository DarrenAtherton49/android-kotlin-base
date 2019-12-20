package com.atherton.sample.presentation.base

import com.jakewharton.rxrelay2.PublishRelay
import com.ww.roxie.BaseAction
import com.ww.roxie.BaseState
import com.ww.roxie.BaseViewModel
import io.reactivex.Observable

abstract class AppViewModel<Action : BaseAction, State : BaseState, ViewEffect : BaseViewEffect>
    : BaseViewModel<Action, State>() {

    protected val viewEffects: PublishRelay<ViewEffect> = PublishRelay.create()

    fun viewEffects(): Observable<ViewEffect> = viewEffects

    protected inline fun viewEffect(viewEffect: () -> ViewEffect) {
        viewEffects.accept(viewEffect.invoke())

        val v = viewEffect.invoke()
        if (v is NavigationViewEffect) {
            v.hi()
        }
    }
}

interface NavigationViewEffect : BaseViewEffect {
    fun hi()
}
