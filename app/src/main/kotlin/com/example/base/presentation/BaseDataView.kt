package com.example.base.presentation

import android.content.Context

/**
 * The view class for each MVP candidate which loads data can implement this interface.
 * For each View in the app (i.e. an Activity or Fragment), you should extend this
 * interface via an interface specific to that View.
 */
interface BaseDataView {

    /**
     * Show a view with a progress bar indicating a loading process.
     */
    fun showProgress()

    /**
     * Hide a loading view.
     */
    fun hideProgress()

    /**
     * Show a retry view in the event of an error when loading data.
     */
    fun showRetry()

    /**
     * Hide a retry view shown previously shown.
     */
    fun hideRetry()

    /**
     * Get a [Context].
     */
    fun context(): Context
}