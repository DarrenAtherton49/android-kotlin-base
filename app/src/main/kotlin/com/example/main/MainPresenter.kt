package com.example.main

import com.example.base.presentation.BasePresenter
import com.example.base.presentation.BaseView
import com.example.common.injection.scope.PerScreen
import javax.inject.Inject

@PerScreen
class MainPresenter @Inject constructor() : BasePresenter<MainPresenter.View>() {

    override fun onViewAttached() {

    }

    override fun onViewDetached() {

    }

    fun onButtonClicked() {
        performViewAction { showMessage() }
    }

    interface View : BaseView {
        fun showMessage()
    }
}