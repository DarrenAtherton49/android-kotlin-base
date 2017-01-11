package com.example.base.presentation

import rx.Subscription
import rx.subscriptions.CompositeSubscription

abstract class BasePresenter<View: BaseView> {

    protected lateinit var view: View
        private set

    protected var isViewAttached = false
        private set

    protected val compositeSubscription: CompositeSubscription by lazy { CompositeSubscription() }

    inline protected fun performViewAction(action: View.() -> Unit) {
        if (isViewAttached) {
            view.action()
        }
    }

    inline protected fun performDomainAction(action: () -> Subscription) {
        compositeSubscription.add(action())
    }

    fun viewAttached(view: View) {
        this.view = view
        isViewAttached = true
        onViewAttached()
    }

    fun viewDetached() {
        isViewAttached = false
        unsubscribeDomainActions()
        onViewDetached()
    }

    /*
     * Implement this method to perform initialisation, subscribe to any reactive
     * streams/observables etc. when the view is attached.
     */
    protected abstract fun onViewAttached()

    /*
     * Implement this method to stop any tasks etc. when the view is detached.
     * Reactive subscriptions will automatically be unsubscribed via unsubscribeDomainActions() if
     * they are added to the compositeSubscription via performDomainAction()
     */
    protected abstract fun onViewDetached()

    private fun unsubscribeDomainActions() {
        compositeSubscription.clear()
    }
}