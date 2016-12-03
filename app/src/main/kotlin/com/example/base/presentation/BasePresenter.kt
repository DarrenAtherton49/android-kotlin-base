package com.example.base.presentation

import android.support.annotation.CallSuper

abstract class BasePresenter<View: BaseView> {

    protected lateinit var view: View

    protected var isViewAttached = false
        private set

    @CallSuper fun onViewAttached(view: View) {
        this.view = view
        isViewAttached = true
    }

    @CallSuper fun onViewDetached() {
        unsubscribe()
        isViewAttached = false
    }

    protected abstract fun unsubscribe()
}