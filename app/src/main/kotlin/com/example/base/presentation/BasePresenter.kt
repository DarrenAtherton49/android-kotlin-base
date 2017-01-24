package com.example.base.presentation

import rx.Subscription
import rx.subscriptions.CompositeSubscription

abstract class BasePresenter<View: BaseView> {

    protected var view: View? = null
        private set

    protected fun isViewAttached() = view != null

    protected val compositeSubscription: CompositeSubscription by lazy { CompositeSubscription() }

    fun viewAttached(view: View) {
        this.view = view
        onViewAttached()
    }

    fun viewDetached() {
        unsubscribeDomainActions()
        onViewDetached()
        view = null
    }

    /*
     * Implement this method to perform initialisation, subscribe to any reactive
     * streams/observables etc. when the view is attached.
     */
    protected abstract fun onViewAttached()

    /*
     * Implement this method to stop any running tasks etc. when the view is detached.
     * Reactive subscriptions will automatically be unsubscribed via unsubscribeDomainActions() if
     * they are added to the compositeSubscription via performDomainAction()
     */
    protected abstract fun onViewDetached()

    inline protected fun performViewAction(action: View.() -> Unit) {
        view?.action()
    }

    inline protected fun performDomainAction(action: () -> Subscription) {
        compositeSubscription.add(action())
    }

    private fun unsubscribeDomainActions() {
        compositeSubscription.clear()
    }
}